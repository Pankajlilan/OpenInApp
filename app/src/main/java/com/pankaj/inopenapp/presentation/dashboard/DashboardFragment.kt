package com.pankaj.inopenapp.presentation.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.pankaj.inopenapp.R
import com.pankaj.inopenapp.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class DashboardFragment : Fragment() {
  
  private var _binding: FragmentDashboardBinding? = null
  private val binding: FragmentDashboardBinding
    get() = _binding!!
  
  private val viewModel: DashboardViewModel by viewModels()
  private val adapter = DashboardAdapter()
  private val args: DashboardFragmentArgs by navArgs()
  
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
    
    lifecycle.coroutineScope.launchWhenCreated {
      viewModel.dashboardData.collect {
        if (it.error.isNotBlank()) {
          Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
        }
        it.data?.let {
        
        }
      }
    }
    setLineChartData()
    
//    val linearLayoutManager =
//      LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
//    binding.recyclerView.layoutManager = linearLayoutManager
//    binding.recyclerView.itemAnimator = DefaultItemAnimator()
//    binding.recyclerView.adapter = adapter
    
  }
  
  fun setLineChartData() {
    
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
    linedataset.setGradientColor(resources.getColor(R.color.colorPrimary), resources.getColor(R.color.white))
//    linedataset.valueTextSize = 20F
    linedataset.fillColor = resources.getColor(R.color.colorPrimary)
//    linedataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
    
    //We connect our data to the UI Screen
    val data = LineData(linedataset)
    binding.getTheGraph.data = data
    binding.getTheGraph.setBackgroundColor(resources.getColor(R.color.white))
    binding.getTheGraph.animateXY(2000, 2000, Easing.EaseInCubic)
    
  }
}