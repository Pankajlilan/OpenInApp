package com.pankaj.inopenapp.presentation.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pankaj.inopenapp.R

/**
 * A simple [Fragment] subclass.
 * Use the [CoursesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CoursesFragment : Fragment() {
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }
  
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_courses, container, false)
  }
  
}