package co.marvel.math.view.list.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import co.marvel.math.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

class ComicViewHolder(itemView: View, private val listener: ResultOptionListener) :
        RecyclerView.ViewHolder(itemView), ResultViewHolder {
    private var resultOption: ComicOption? = null
    private var rootView: View? = null
    private var nameView: TextView? = null
    private var descriptionView: TextView? = null
    private var thumbnailView: ImageView? = null

    init {
        rootView = itemView.findViewById(R.id.rootView) as View
        rootView?.setOnClickListener {
            resultOption?.let { option ->
                listener.onClickComic(option.id)
            }
        }
        nameView = itemView.findViewById(R.id.title)
        descriptionView = itemView.findViewById(R.id.description)
        thumbnailView = itemView.findViewById(R.id.thumbnail)
    }

    override fun bindViews(option: ResultOption) {
        this.resultOption = option as ComicOption
        nameView?.text = option.name
        option.description?.let {
            if (it.isNotBlank()) {
                descriptionView?.text = it
            } else {
                descriptionView?.text = itemView.resources.getString(R.string.notSpecify)
            }
        } ?: run {
            descriptionView?.text = itemView.resources.getString(R.string.notSpecify)
        }

        thumbnailView?.let {
            Glide
                    .with(itemView.context)
                    .load(option.thumbnail)
                    .transition(withCrossFade())
                    .into(it)
        }
    }
}
