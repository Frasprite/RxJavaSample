package test.rxjava.com.rxjavasample

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import org.jetbrains.anko.toast

import java.util.ArrayList

/**
 * Adapter used to map a String to a text view.
 */
class SimpleStringAdapter(private val mContext: Context) : RecyclerView.Adapter<SimpleStringAdapter.ViewHolder>() {
    private val mStrings = ArrayList<String>()

    fun setStrings(newStrings: List<String>) {
        mStrings.clear()
        mStrings.addAll(newStrings)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.colorTextView.text = mStrings[position]
        holder.itemView.setOnClickListener {
            mContext.toast(mStrings[position])
        }
    }

    override fun getItemCount(): Int {
        return mStrings.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val colorTextView: TextView = view.findViewById<View>(R.id.colorDisplay) as TextView

    }
}
