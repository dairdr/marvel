package co.marvel.math.view.list.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.marvel.math.R
import co.marvel.math.utils.AutoUpdatableAdapter
import kotlin.properties.Delegates

class ResultAdapter(
        private val inflater: LayoutInflater,
        private val listener: ResultOptionListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), AutoUpdatableAdapter {
    var data: List<ResultOption> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { old, new -> old.id == new.id }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CHARACTER ->
                CharacterViewHolder(
                        inflater.inflate(
                                R.layout.item_list_result, parent, false), listener)
            TYPE_COMIC ->
                ComicViewHolder(
                        inflater.inflate(
                                R.layout.item_list_result, parent, false), listener)
            TYPE_CREATOR ->
                CreatorViewHolder(
                        inflater.inflate(
                                R.layout.item_list_result, parent, false), listener)
            TYPE_EVENT ->
                EventViewHolder(
                        inflater.inflate(
                                R.layout.item_list_result, parent, false), listener)
            TYPE_SERIE ->
                SerieViewHolder(
                        inflater.inflate(
                                R.layout.item_list_result, parent, false), listener)
            else ->
                StoryViewHolder(
                        inflater.inflate(
                                R.layout.item_list_result, parent, false), listener)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ResultViewHolder).bindViews(data[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position].type) {
            ResultOption.TYPE.CHARACTER -> TYPE_CHARACTER
            ResultOption.TYPE.COMIC -> TYPE_COMIC
            ResultOption.TYPE.CREATOR -> TYPE_CREATOR
            ResultOption.TYPE.EVENT -> TYPE_EVENT
            ResultOption.TYPE.SERIE -> TYPE_SERIE
            else -> TYPE_STORY
        }
    }

    companion object {
        const val TYPE_CHARACTER = 0
        const val TYPE_COMIC = 1
        const val TYPE_CREATOR = 2
        const val TYPE_EVENT = 3
        const val TYPE_SERIE = 4
        const val TYPE_STORY = 5
    }
}