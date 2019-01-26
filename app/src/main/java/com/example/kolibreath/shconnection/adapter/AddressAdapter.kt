package com.example.kolibreath.shconnection.adapter

import ID
import USER_TYPE
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.kolibreath.shconnection.R
import com.example.kolibreath.shconnection.base.People
import org.jetbrains.anko.layoutInflater


/**
 * 班级通讯录elvAdapter
 */
class AddressAdapter: BaseExpandableListAdapter {

    var context: Context? = null
    var group: List<String>? = null
    var child: List<List<People>>? = null
    var inflater: LayoutInflater? = null

    constructor(context: Context, parent: List<String>,child: List<List<People>>){
        this.child = child
        this.group = parent
        this.context = context
        this.inflater = context.layoutInflater
    }

    override fun getGroupCount(): Int {
        return group!!.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return child!![groupPosition].size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return child!![groupPosition][childPosition]
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
        var groupHolder: GroupHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_address_group, parent, false)
            groupHolder = GroupHolder(convertView)
            groupHolder.textView = convertView.findViewById(R.id.tv_address_group) as TextView
            convertView.tag = groupHolder
        } else {
            groupHolder = convertView.tag as GroupHolder
        }
        groupHolder.textView.text = group!![groupPosition]
        return convertView as View
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        var childHolder :ChildHolder
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_address_child,parent,false)
            childHolder = ChildHolder(convertView)
            childHolder.textView = convertView.findViewById(R.id.tv_address_child_name) as TextView
            convertView.tag = childHolder
        }else{
            childHolder = convertView.tag as ChildHolder
        }
        childHolder.textView.text = child!![groupPosition][childPosition].name
        return convertView as View
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    class GroupHolder(convertView: View?){
         var textView: TextView = convertView!!.findViewById(R.id.tv_address_group)
    }
    class ChildHolder(convertView: View?){
        var textView: TextView = convertView!!.findViewById(R.id.tv_address_child_name)
    }

}