package top.sunhy.base.adapter

import com.drakeet.multitype.MultiTypeAdapter

/**
 * 增加数据更新相关的方法 局部更新 优化性能
 */
open class BaseMultiAdapter @JvmOverloads constructor(
    private val datas: List<Any> = emptyList()
) : MultiTypeAdapter(datas) {

    /**
     * 整体变更
     */
    fun setNewData(list: List<Any>) {
        items = list
        notifyDataSetChanged()
    }

    /**
     * 根据position移除
     */
    fun remove(position: Int) {
        if (position < items.size) {
            (items as MutableList<Any>).removeAt(position)
            notifyItemRemoved(position)
        }
    }

    /**
     * 添加一个 item
     */
    fun addData(item: Any) {
        if (item == null) {
            return
        }
        if (items.isNullOrEmpty()) {
            (items as MutableList<Any>).add(item)
            notifyDataSetChanged()
        } else {
            (items as MutableList<Any>).add(item)
            notifyItemInserted(items.size - 1)
        }
    }

    /**
     * 添加一个 item
     */
    fun addData(position: Int, item: Any) {
        if (item == null) {
            return
        }
        if (items.isNullOrEmpty()) {
            (items as MutableList<Any>).add(item)
            notifyDataSetChanged()
        } else {
            (items as MutableList<Any>).add(position, item)
            notifyItemInserted(position)
        }
    }

    /**
     * 添加一组 list
     */
    fun addDatas(list: List<Any>) {
        if (list.isNullOrEmpty()) {
            return
        }
        if (items.isNullOrEmpty()) {
            (items as MutableList<Any>).addAll(list)
            notifyDataSetChanged()
        } else {
            (items as MutableList<Any>).addAll(list)
            notifyItemRangeInserted(items.size - list.size, list.size)
        }

    }

    /**
     * 添加一组 list
     */
    fun addDatas(position: Int, list: List<Any>) {
        if (list.isNullOrEmpty()) {
            return
        }
        if (items.isNullOrEmpty()) {
            (items as MutableList<Any>).addAll(list)
            notifyDataSetChanged()
        } else {
            (items as MutableList<Any>).addAll(position, list)
            notifyItemRangeInserted(position, items.size)
        }
    }

    /**
     * 清空数据 list
     */

    fun clearDatas() {
        if (!items.isNullOrEmpty()) {
            (items as MutableList<Any>).clear()
        }

        notifyDataSetChanged()
    }
}