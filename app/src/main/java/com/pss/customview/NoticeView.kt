package com.pss.customview

import android.content.Context
import android.content.res.Resources.getSystem
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.card.MaterialCardView

class NoticeView @JvmOverloads constructor(
    context : Context, attr: AttributeSet? = null, defStyleAttr: Int = 0
): MaterialCardView(context, attr, defStyleAttr) {

    private val parentConstraint: ConstraintLayout by lazy { ConstraintLayout(context) }
    private val constraintSet: ConstraintSet by lazy { ConstraintSet() }
    private val leftIconView: AppCompatImageView by lazy { AppCompatImageView(context) }
    private val infoTextView: AppCompatTextView by lazy { AppCompatTextView(context) }


    init {
        createView()
    }

    private fun createView() {
        updateCardView()
        addChildView()
    }

    private fun updateCardView() {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        radius = 24f
        setPadding(12.dp, 12.dp, 12.dp, 12.dp)
        setCardBackgroundColor(Color.MAGENTA)
    }

    private fun addChildView() {
        constraintSet.clone(parentConstraint) //parentConstraint 의 설정을 복제
        addParentConstraintView() //parentConstraint 크기 결정, 부모 뷰의 자식 뷰로 설정
        addLeftIconToParent() //왼쪽 아이콘 설정 및 위치 설정
        addInfoTextView()
        constraintSet.applyTo(parentConstraint) //parentConstraint 에 설정한 constraintSet 제약 조건을 적용
    }

    private fun addParentConstraintView() {
        parentConstraint.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        addView(parentConstraint)
    }

    private fun addLeftIconToParent() {
        leftIconView.apply {
            id = generateViewId()
            setImageResource(R.drawable.ic_baseline_compass_calibration_24)
            setPadding(12.dp, 12.dp, 12.dp, 12.dp)
            parentConstraint.addView(leftIconView)
            setLeftIconConstraints()
        }
    }

    private fun addInfoTextView() {
        infoTextView.apply {
            id = generateViewId()
            text = "이것은 노션뷰입니다"
            textSize = 48.sp
            setTextColor(Color.WHITE)
        }
        parentConstraint.addView(infoTextView)
        setInfoTextViewConstraint()
    }

    //설명 글 크기 및 위치 설정
    private fun setInfoTextViewConstraint() {
        constraintSet.constrainWidth(infoTextView.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainHeight(infoTextView.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.connect(infoTextView.id, ConstraintSet.START, leftIconView.id, ConstraintSet.END)
        constraintSet.connect(infoTextView.id, ConstraintSet.TOP, leftIconView.id, ConstraintSet.TOP)
        constraintSet.connect(infoTextView.id, ConstraintSet.BOTTOM, leftIconView.id, ConstraintSet.BOTTOM)
    }

    //아이콘 크기 및 위치 설정
    private fun setLeftIconConstraints() {
        constraintSet.constrainWidth(leftIconView.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.constrainHeight(leftIconView.id, ConstraintSet.WRAP_CONTENT)
        constraintSet.connect(
            leftIconView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP
        )
        constraintSet.connect(
            leftIconView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START
        )
    }

}

val Int.dp : Int get() = this * getSystem().displayMetrics.density.toInt()

val Int.sp: Float get() = this/ getSystem().displayMetrics.scaledDensity