package com.pankaj.inopenapp.presentation.dashboard

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.pankaj.inopenapp.R
import com.pankaj.inopenapp.common.Constants
import com.pankaj.inopenapp.common.convertDateToFloat
import com.pankaj.inopenapp.common.convertDateToTitleFormat
import com.pankaj.inopenapp.common.openWhatsAppChat
import com.pankaj.inopenapp.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class DashboardFragment : Fragment() {
  
  private var _binding: FragmentDashboardBinding? = null
  private val binding: FragmentDashboardBinding
    get() = _binding!!
  
  private val viewModel: DashboardViewModel by viewModels()
  private val args: DashboardFragmentArgs by navArgs()
  
  var isTopLinkSelected = true
  
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentDashboardBinding.inflate(inflater, container, false)
    
    binding.fab.bringToFront()
    handleMenuItems()
    
    return _binding?.root
  }
  
  private fun handleMenuItems() {
    binding.fab.setOnClickListener {
      Toast.makeText(requireContext(), "Adding Links to your profile", Toast.LENGTH_SHORT)
        .show()
    }
    binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
      when (menuItem.itemId) {
        
        R.id.menu_courses -> {
          findNavController().navigate(DashboardFragmentDirections.navigateToCoursesFragment())
          true
        }
        
        R.id.menu_campaigns -> {
          findNavController().navigate(DashboardFragmentDirections.navigateToCampaignsFragment())
          true
        }
        
        R.id.menu_profiles -> {
          findNavController().navigate(DashboardFragmentDirections.navigateToCampaignsFragment())
          true
        }
        
        else -> false
      }
    }
  }
  
  @RequiresApi(Build.VERSION_CODES.N)
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    args.bearerToken?.let {
      viewModel.getDashboardData()
    }
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      binding.tvGreetings.text = getGreeting()
    }
    
    lifecycle.coroutineScope.launchWhenCreated {
      viewModel.dashboardData.collect {
        if (it.error.isNotBlank()) {
          Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
        }
        it.data?.let { dashboard ->
          binding.apply {

//          Assigning data to Top Links Recycler View
            topLinksRecyclerView.layoutManager =
              LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            topLinksRecyclerView.itemAnimator = DefaultItemAnimator()
            val topLinkAdapter = TopLinksAdapter(dashboard.data.topLinks, requireContext())
            topLinksRecyclerView.adapter = topLinkAdapter

//          Assigning data to Recent Links Recycler View
            recentLinksRecyclerView.layoutManager =
              LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            recentLinksRecyclerView.itemAnimator = DefaultItemAnimator()
            val recentLinksAdapter =
              RecentLinksAdapter(dashboard.data.recentLinks, requireContext())
            recentLinksRecyclerView.adapter = recentLinksAdapter
            
            switchLinks()
            setLineChartData(dashboard.data.overallUrlChart)
            
            dashboardDetails = dashboard
            lifecycleOwner = requireActivity()
            executePendingBindings()
            invalidateAll()
            
            llTalkWithUs.setOnClickListener { view ->
              view.openWhatsAppChat(dashboard.supportWhatsappNumber)
            }
            
            llFaQ.setOnClickListener {
              val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.FAQ))
              requireContext().startActivity(intent)
            }
            
            btnDashboardTopLinks.setOnClickListener {
              if (!isTopLinkSelected) {
                isTopLinkSelected = true
                switchLinks()
              }
            }
            btnDashboardRecentLinks.setOnClickListener {
              if (isTopLinkSelected) {
                isTopLinkSelected = false
                switchLinks()
              }
            }
          }
        }
      }
    }
  }
  
  override fun onResume() {
    super.onResume()
    binding.bottomNavigation.selectedItemId = R.id.menu_links
  }
  
  private fun switchLinks() {
    if (isTopLinkSelected) {
      binding.apply {
        btnDashboardTopLinks.setTextColor(
          ContextCompat.getColor(
            requireContext(),
            R.color.white
          )
        )
        btnDashboardTopLinks.background = ContextCompat.getDrawable(
          requireContext(),
          R.drawable.curved_background
        )
        topLinksRecyclerView.visibility = View.VISIBLE
        btnDashboardRecentLinks.setTextColor(
          ContextCompat.getColor(
            requireContext(),
            R.color.unselected_color
          )
        )
        btnDashboardRecentLinks.background = null
        recentLinksRecyclerView.visibility = View.INVISIBLE
        
      }
    } else {
      binding.apply {
        btnDashboardRecentLinks.setTextColor(
          ContextCompat.getColor(
            requireContext(),
            R.color.white
          )
        )
        btnDashboardRecentLinks.background = ContextCompat.getDrawable(
          requireContext(),
          R.drawable.curved_background
        )
        recentLinksRecyclerView.visibility = View.VISIBLE
        btnDashboardTopLinks.setTextColor(
          ContextCompat.getColor(
            requireContext(),
            R.color.unselected_color
          )
        )
        btnDashboardTopLinks.background = null
        topLinksRecyclerView.visibility = View.INVISIBLE
      }
    }
  }
  
  @RequiresApi(Build.VERSION_CODES.N)
  private fun setLineChartData(overallUrlChart: Map<String, Int>) {
    
    val firstKey = overallUrlChart.keys.firstOrNull()?.convertDateToTitleFormat()
    val lastKey = overallUrlChart.keys.lastOrNull()?.convertDateToTitleFormat()
    
    binding.tvChartRange.text =
      String.format(resources.getString(R.string.chart_date_range), firstKey, lastKey)
    
    val lineValues = ArrayList<Entry>()
    overallUrlChart.forEach { (index, entry) ->
      lineValues.add(Entry(index.convertDateToFloat(), entry.toFloat()))
    }
    
    val lineDataSet = LineDataSet(lineValues, "First")
    lineDataSet.apply {
      disableDashedLine()
      //We add features to our chart
      color = resources.getColor(R.color.colorPrimary)
      
      setGradientColor(
        resources.getColor(R.color.colorPrimary),
        resources.getColor(R.color.white)
      )
      fillColor = resources.getColor(R.color.colorPrimary)
      color = resources.getColor(R.color.colorPrimary)
      setDrawFilled(true)
      fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.gradient_fill)
      setDrawValues(false) // Disable value text
      setDrawCircles(false) // Disable value text
      lineWidth = 2f // Change this value to the desired thickness
    }
    
    val data = LineData(lineDataSet)
    binding.getTheGraph.data = data
    
    // Hide left and right axis labels
    binding.getTheGraph.axisRight.isEnabled = false
    binding.getTheGraph.xAxis.setDrawAxisLine(false)
    binding.getTheGraph.xAxis.position = XAxis.XAxisPosition.BOTTOM

// Hide background grid lines
    binding.getTheGraph.xAxis.setDrawGridLines(true)
    
    binding.getTheGraph.description.isEnabled = false
    binding.getTheGraph.legend.isEnabled = false
    
    // Disable drawing x-axis line
    binding.getTheGraph.xAxis.setDrawAxisLine(false)

// Disable drawing y-axis line
    binding.getTheGraph.axisLeft.setDrawAxisLine(false)
    binding.getTheGraph.axisRight.setDrawAxisLine(false)
    
    // Set text color for left y-axis labels
    binding.getTheGraph.axisLeft.textColor = Color.LTGRAY

// Set text color for bottom x-axis labels
    binding.getTheGraph.xAxis.textColor = Color.LTGRAY
    
    binding.getTheGraph.setBackgroundColor(resources.getColor(R.color.white))
    binding.getTheGraph.animateXY(2000, 2000, Easing.EaseInCubic)
    
  }
  
  @RequiresApi(Build.VERSION_CODES.O)
  fun getGreeting(): String {
    val currentTime = java.time.LocalTime.now()
    return when (currentTime.hour) {
      in 6..11 -> getString(R.string.good_morning)
      in 12..16 -> getString(R.string.good_afternoon)
      in 17..20 -> getString(R.string.good_evening)
      else -> getString(R.string.good_night)
    }
  }
}