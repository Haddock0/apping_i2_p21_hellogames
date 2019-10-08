package fr.epita.game_list

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_game_list.view.*

class GameAdapterList(val context : Context,
                      val data : List<Game>,
                      private val onItemClickListener: View.OnClickListener) :
        RecyclerView.Adapter<GameAdapterList.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // retrieve the item at the specified position
        val currentItem = data[position]
        // put the data
        holder!!.nameTextView.text = currentItem.name
        Glide
            .with(holder.itemView)
            .load(currentItem.picture)
            .centerCrop()
            .into(holder.logoImageView)
        holder.itemView.tag = position
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.text_list)
        val logoImageView: ImageView = itemView.findViewById(R.id.image_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create the row from a layout inflater
        val rowView = LayoutInflater
            .from(context)
            .inflate(R.layout.activity_game_list, parent, false)

        // attach the onclicklistener
        rowView.setOnClickListener(onItemClickListener)

        // create a ViewHolder using this rowview
        val viewHolder = ViewHolder(rowView)
        // return this ViewHolder. The system will make sure view holders
        // are used and recycled
        return viewHolder
    }


    override fun getItemCount(): Int {
        return data.size
    }


}
