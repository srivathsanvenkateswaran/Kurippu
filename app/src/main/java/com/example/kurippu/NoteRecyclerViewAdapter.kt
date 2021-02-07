package com.example.kurippu

import android.content.Context
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteRecyclerViewAdapter(private val context: Context, private val listener: INotesRecyclerViewAdapter) : RecyclerView.Adapter<NoteRecyclerViewAdapter.NoteViewHolder>() {

    val allNotes: ArrayList<Note> = ArrayList<Note>()

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.noteText)
        val deleteButton = itemView.findViewById<ImageView>(R.id.deleteImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder : NoteViewHolder = NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note, parent, false))
        viewHolder.deleteButton.setOnClickListener{
            listener.onItemClicked(allNotes[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = allNotes[position]
        holder.textView.text = currentNote.text
    }

    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)

        notifyDataSetChanged()
    }
}

interface INotesRecyclerViewAdapter{
    fun onItemClicked(note : Note)
}