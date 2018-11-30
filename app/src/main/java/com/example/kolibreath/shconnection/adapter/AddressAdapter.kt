package com.example.kolibreath.shconnection.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.AddressBean
import org.jetbrains.anko.find

/**
 * 班级通讯录elvAdapter
 */
class AddressAdapter: BaseExpandableListAdapter {

    var context: Context? = null
    var group: List<String>? = null
    var child: List<List<String>>? = null
    var inflater: LayoutInflater? = null

    constructor(context: Context, parent: List<String>,child: List<List<String>>){
        this.child = child
        this.group = parent
        this.context = context
    }

    override fun getGroupCount(): Int {
        return group!!.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return group!![groupPosition].length
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return group!![groupPosition][childPosition]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroup(groupPosition: Int): Any {
        return group!![groupPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return 0
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.item_address_group, parent, false)
        }
        (convertView as TextView).text = getGroup(groupPosition) as String

        return convertView

    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (convertView == null){
            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val itemType = getChildType(groupPosition, childPosition)
            when(itemType){
                0 -> convertView
            }
        }
        return convertView as View
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    class GroupHolder(convertView: View?){
        var textView:TextView = convertView!!.find(R.id.tv_address_group)

    }

    class ChildHolder(convertView: View?){
        var textView:TextView = convertView!!.find(R.id.tv_address_child_name)
    }
}