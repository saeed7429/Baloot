package ms.saghafi.baloot.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ms.saghafi.baloot.utils.IOnItemClickListener
import com.squareup.picasso.Picasso
import ms.saghafi.baloot.databinding.ItemArticleBinding
import ms.saghafi.baloot.model.Article

class ArticlesAdapter(private val articleClickListener: IOnItemClickListener<Article>) :
    ListAdapter<Article, ArticlesAdapter.ViewHolder>(ArticleDiffCallBack()) {

    class ViewHolder private constructor(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article, articleClickListener: IOnItemClickListener<Article>){

            binding.itemArticleTextView.text = item.title

            item.urlToImage?.let {
                Picasso.get()
                    .load(it)
                    .fit()
                    .into(binding.itemArticleImageView)
            }

            binding.root.setOnClickListener {
                articleClickListener.onClick(item)
            }
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemArticleBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,articleClickListener)
    }

}


class ArticleDiffCallBack : DiffUtil.ItemCallback<Article>(){
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

}

