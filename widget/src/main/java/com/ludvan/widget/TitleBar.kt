package com.ludvan.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.ludvan.widget.databinding.TitleBarBinding

class TitleBar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    companion object {
        const val ACTION_STYLE_TEXT = 1
        const val ACTION_STYLE_BUTTON = 2
    }

    var helpClickListener: ((View) -> Unit)? = null
    var actionClickListener: ((View) -> Unit)? = null

    private var binding: TitleBarBinding = TitleBarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        val title = ta.getString(R.styleable.TitleBar_title) ?: "标题"
        val actionName = ta.getString(R.styleable.TitleBar_actionName) ?: "动作"
        val enableHelp = ta.getBoolean(R.styleable.TitleBar_enableHelp, false)
        val enableAction = ta.getBoolean(R.styleable.TitleBar_enableAction, false)
        val actionStyle = ta.getInt(R.styleable.TitleBar_actionStyle, ACTION_STYLE_TEXT)
        val hideDivider = ta.getBoolean(R.styleable.TitleBar_hideDivider, false)
        binding.ivTitleHelp.visibility = if (enableHelp) {
            binding.ivTitleHelp.setOnClickListener { helpClickListener?.invoke(it) }
            VISIBLE
        } else GONE
        binding.tvAction.visibility = if (enableAction) {
            binding.tvAction.setOnClickListener { actionClickListener?.invoke(it) }
            VISIBLE
        } else GONE
        setTitle(title)
        setActionName(actionName)
        setActionStyle(actionStyle)
        if (hideDivider) {
            hideDivider()
        }
        ta.recycle()
    }

    fun setTitle(title: String) {
        binding.tvTitle.text = title
    }

    fun setActionStyle(style: Int) {
        if (style == ACTION_STYLE_TEXT) {
            binding.tvAction.setTextColor(resources.getColor(R.color.black333, context.theme))
//            binding.tvAction.textSize = 14F
        } else if (style == ACTION_STYLE_BUTTON) {
            binding.tvAction.setTextColor(resources.getColor(R.color.gray666, context.theme))
//            binding.tvAction.setBackgroundResource(R.drawable.title_bar_action_style_bgr)
        }
    }

    fun setActionName(name: String) {
        binding.tvAction.text = name
    }

    fun hideDivider() {
        binding.divider.visibility = View.GONE
    }

    fun bind(activity: Activity) {
        binding.ivBack.setOnClickListener {
            activity.onBackPressed()
        }
    }

    fun bind(fragment: Fragment) {
        binding.ivBack.setOnClickListener {
            fragment.requireActivity().onBackPressed()
        }
    }

}