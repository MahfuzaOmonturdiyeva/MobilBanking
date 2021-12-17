package uz.gita.mobilbanking.ui.screenMain.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.data.response.CardDataResponse

class CardAdapter{
//    context: Context,
//    private val resource: Int,
//    private val list: Array<CardDataResponse>
//) :
//    BaseAdapter {
//
//override fun getViewTypeCount(): Int {
//        return list.size
//    }
//
//    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
//        var convertView = convertView
//        if (convertView == null) {
//            convertView = LayoutInflater.from(parent.context).inflate(resource, parent, false)
//        }
//        val pan = convertView!!.findViewById<TextView>(R.id.tv_card_number_item)
//        val name = convertView!!.findViewById<EditText>(R.id.ed_name_card_item)
//        val exp = convertView!!.findViewById<TextView>(R.id.t_v_expiration_date_item)
//        pan.text = list.get(position).pan
//
//        name.setText(list[position].cardName)
//        exp.text = list[position].exp
//        return convertView
//    }
}