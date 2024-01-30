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
    return _binding?.root
  }
  
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
          val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
          
          binding.apply {
            topLinksRecyclerView.layoutManager = linearLayoutManager
            topLinksRecyclerView.itemAnimator = DefaultItemAnimator()
            val adapter = DashboardAdapter(dashboard.data.topLinks, requireContext())
            topLinksRecyclerView.adapter = adapter
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
              if (!isTopLinkSelected){
                isTopLinkSelected = true
                switchLinks()
              }
            }
            btnDashboardRecentLinks.setOnClickListener {
              if (isTopLinkSelected){
                isTopLinkSelected = false
                switchLinks()
              }
            }
          }
        }
      }
    }
    setLineChartData()
  }
  
  private fun switchLinks() {
    if(isTopLinkSelected){
      binding.apply {
        btnDashboardTopLinks.setTextColor(ContextCompat.getColor(requireContext(),R.color.unselected_color))
      }
    } else {
    
    }
  }
  
  private fun setLineChartData() {
    
    val linevalues = ArrayList<Entry>()
    linevalues.add(Entry(20f, 0.0F))
    linevalues.add(Entry(30f, 3.0F))
    linevalues.add(Entry(40f, 2.0F))
    linevalues.add(Entry(50f, 1.0F))
    linevalues.add(Entry(60f, 8.0F))
    linevalues.add(Entry(70f, 10.0F))
    linevalues.add(Entry(80f, 1.0F))
    linevalues.add(Entry(90f, 2.0F))
    linevalues.add(Entry(100f, 5.0F))
    linevalues.add(Entry(110f, 1.0F))
    linevalues.add(Entry(120f, 20.0F))
    linevalues.add(Entry(130f, 40.0F))
    linevalues.add(Entry(140f, 50.0F))
    
    val linedataset = LineDataSet(linevalues, "First")
    linedataset.disableDashedLine()
    //We add features to our chart
    linedataset.color = resources.getColor(R.color.colorPrimary)

//    linedataset.circleRadius = 10f
//    linedataset.setDrawFilled(true)
    linedataset.setGradientColor(
      resources.getColor(R.color.colorPrimary),
      resources.getColor(R.color.white)
    )
//    linedataset.valueTextSize = 20F
    linedataset.fillColor = resources.getColor(R.color.colorPrimary)
    linedataset.color = resources.getColor(R.color.colorPrimary)
    linedataset.setDrawFilled(true)
    linedataset.fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.gradient_fill)
    linedataset.setDrawValues(false) // Disable value text
    linedataset.setDrawCircles(false) // Disable value text
    linedataset.lineWidth = 2f // Change this value to the desired thickness


//    linedataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
    
    //We connect our data to the UI Screen
    
    val data = LineData(linedataset)
    binding.getTheGraph.data = data
    
    // Hide left and right axis labels
//    binding.getTheGraph.axisLeft.isEnabled = false
    binding.getTheGraph.axisRight.isEnabled = false
    binding.getTheGraph.xAxis.setDrawAxisLine(false)
    binding.getTheGraph.xAxis.position = XAxis.XAxisPosition.BOTTOM

// Hide background grid lines
    binding.getTheGraph.xAxis.setDrawGridLines(true)
//    binding.getTheGraph.axisLeft.setDrawGridLines(false)
//    binding.getTheGraph.axisRight.setDrawGridLines(false)
    
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