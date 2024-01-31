package com.pankaj.inopenapp.presentation.dashboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pankaj.inopenapp.databinding.ItemLinksLayoutBinding
import com.pankaj.inopenapp.domain.model.Link
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RecentLinksAdapter(private val list: MutableList<Link>, private val requireContext: Context) :
  RecyclerView.Adapter<RecentLinksAdapter.MyViewHolder>() {
  
  private var listener: ((Link) -> Unit)? = null
  
  class MyViewHolder(val viewHolder: ItemLinksLayoutBinding) :
    RecyclerView.ViewHolder(viewHolder.root)
  
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): MyViewHolder {
    val binding =
      ItemLinksLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MyViewHolder(binding)
  }
  
  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val link: Link = list[position]
    
    holder.viewHolder.apply {
      tvLinkTitle.text = link.title
      tvLinkDate.text = formatDate(link.createdAt)
      tvLinkClicks.text = link.totalClicks.toString()
      tvLinkURL.text = link.webLink
      ivLinkCopy.setOnClickListener {
        copyToClipboard(requireContext, link.webLink)
      }
      root.setOnClickListener {
        listener?.let {
          it(list[position])
        }
      }
    }
  }
  
  private fun formatDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    
    // Parse input date string
    val date = inputFormat.parse(inputDate)
    
    // Format date to desired output format
    return outputFormat.format(date as Date)
  }
  
  override fun getItemCount(): Int {
    return this.list.size
  }
  
  private fun copyToClipboard(context: Context, text: String, label: String = "Copied Text") {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(requireContext, "Text Copied to Clipboard", Toast.LENGTH_SHORT).show()
  }
  
  fun getThumbnailFromUrl(url: String, targetImageView: ImageView) {
    val retriever = MediaMetadataRetriever()
    try {
      retriever.setDataSource(requireContext, Uri.parse(url))
      val bitmap = retriever.getFrameAtTime(1)
      // Load the fetched thumbnail into ImageView using Glide
      Glide.with(requireContext)
        .load(bitmap)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true) // Skip memory cache to avoid OutOfMemoryError
        .into(targetImageView)
    } catch (e: Exception) {
      e.printStackTrace()
    } finally {
      retriever.release()
    }
  }
}