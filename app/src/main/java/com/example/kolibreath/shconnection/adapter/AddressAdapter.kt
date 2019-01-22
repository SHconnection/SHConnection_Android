package com.example.kolibreath.shconnection.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.Person

/**
 * 班级通讯录elvAdapter
 */
class AddressAdapter: BaseExpandableListAdapter {

    var context: Context? = null
    var group: List<String>? = null
    var child: List<List<Person>>? = null
    var inflater: LayoutInflater? = null

    constructor(context: Context, parent: List<String>,child: List<List<Person>>){
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
        val group = convertView?.findViewById<TextView>(R.id.tv_address_group)
        (convertView as TextView).text = getGroup(groupPosition) as String

        return convertView

    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (convertView == null){
            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.item_address_child,parent,false)

        }
        val name = convertView?.findViewById<TextView>(R.id.tv_address_child_name)
        name?.text = child?.get(groupPosition)?.get(childPosition)?.name
//        tvName?.setOnClickListener {
//            val intent = Intent()
//            intent.setClass(convertView?.context,UserProfile::class.java)
//        }
        return convertView as View
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

}