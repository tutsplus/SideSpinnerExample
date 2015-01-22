package com.cindypotvin.sidespinnerexample;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Represents a spinner control that can be scrolled using side arrows.
 */
public class SideSpinner extends LinearLayout {

	/**
	 * The state to save for the selected index of the spinner.
	 */
	private static String STATE_SELECTED_INDEX = "SelectedIndex";
	
	/**
	 * The state to save to keep the state of the super class correctly.
	 */
	private static String STATE_SUPER_CLASS = "SuperClass";
	
	/**
	 * The values in the spinner.
	 */
	private CharSequence[] mSpinnerValues = null;
	
	/**
	 * The currently selected index in the spinner.
	 */
	private int mSelectedIndex = -1;
	
	/**
	 * The button to show the previous value in the list.
	 */
	private Button mPreviousButton;
	
	/**
	 * The button to show the next value in the list.
	 */
	private Button mNextButton;

	public SideSpinner(Context context) {
		super(context);

		initializeViews(context);
	}

	public SideSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SideSpinner);
		mSpinnerValues = typedArray.getTextArray(R.styleable.SideSpinner_values);
		typedArray.recycle();

		initializeViews(context);
	}

	public SideSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SideSpinner);
		mSpinnerValues = typedArray.getTextArray(R.styleable.SideSpinner_values);
		typedArray.recycle();

		initializeViews(context);
	}

	/**
	 * Inflates the views in the layout.
	 * 
	 * @param context
	 *            the current context for the control.
	 */
	private void initializeViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		inflater.inflate(R.layout.sidespinner_view, this);
	}

	/**
	 * Sets the list of values in the spinner, selecting the first value by default.
	 * @param values 
	 *           the values to set in the spinner.
	 */
	public void setValues(CharSequence[] values) {	
		mSpinnerValues = values;
		
		// Select the first item of the string array by default since the list of values changed
		setSelectedIndex(0);
	}
	
	/**
	 * Gets the list of values in the spinner.
	 * 
	 * @return the values in the spinner.
	 */
	public CharSequence[] getValues() { 
		return (mSpinnerValues);
		}
	
	@Override
	protected void onFinishInflate() {

		// When the controls in the layout are doing being inflated, set the
		// callbacks for the side arrows
		super.onFinishInflate();

		// When the previous button is pressed, select the previous item in the
		// list
		mPreviousButton = (Button)this.findViewById(R.id.sidespinner_view_previous);
		mPreviousButton.setBackgroundResource(android.R.drawable.ic_media_previous);
		
		mPreviousButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (mSelectedIndex > 0) {
					int newSelectedIndex = mSelectedIndex - 1;
					setSelectedIndex(newSelectedIndex);
				}
			}
		});

		// When the next button is pressed, select the next item in the list
		mNextButton = (Button)this.findViewById(R.id.sidespinner_view_next);
		mNextButton.setBackgroundResource(android.R.drawable.ic_media_next);
		mNextButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (mSpinnerValues != null && mSelectedIndex < mSpinnerValues.length - 1) {
					int newSelectedIndex = mSelectedIndex + 1;
					setSelectedIndex(newSelectedIndex);
				}
			}
		});
		
		// Select the first item of the string array by default
		setSelectedIndex(0);
	}
	
	/**
	 * Sets the selected index of the spinner.
	 * 
	 * @param index
	 *            the index of the value to select.
	 */
	public void setSelectedIndex(int index) {
		// If no values are set for the spinner, do nothing
		if (mSpinnerValues == null || mSpinnerValues.length == 0)
			return;
		
		// If the index value is invalid, do nothing
		if (index < 0 || index >= mSpinnerValues.length)
			return;
		
		// Set the current index and display the value
		mSelectedIndex = index;
		TextView currentValue = (TextView)this.findViewById(R.id.sidespinner_view_value);
		currentValue.setText(mSpinnerValues[index]);
		
		// If the first value is show, hide the previous button
		if (mSelectedIndex == 0)
			mPreviousButton.setVisibility(INVISIBLE);
		else
			mPreviousButton.setVisibility(VISIBLE);
		
		//  If the last value is shown, hide the next button
		if (mSelectedIndex == mSpinnerValues.length - 1)
			mNextButton.setVisibility(INVISIBLE);
		else
			mNextButton.setVisibility(VISIBLE);
	}
	
	/**
	 * Gets the selected value of the spinner, or null if no valid selected index is set yet.
	 * 
	 * @return the selected value of the spinner.
	 */
	public CharSequence getSelectedValue() {
		// If no values are set for the spinner, return an empty string
		if (mSpinnerValues == null || mSpinnerValues.length == 0)
			return "";
		
		// If the current index  is invalid, return an empty string
		if (mSelectedIndex < 0 || mSelectedIndex >= mSpinnerValues.length)
			return "";
		
		return mSpinnerValues[mSelectedIndex];
	}
	
	/**
	 * Gets the selected index of the spinner.
	 * 
	 * @return the selected index of the spinner.
	 */
	public int getSelectedIndex() {
		return mSelectedIndex;
	}
	
	
	@Override
	protected Parcelable onSaveInstanceState() { 
		Bundle bundle = new Bundle();

	    bundle.putParcelable(STATE_SUPER_CLASS, super.onSaveInstanceState());
	    bundle.putInt(STATE_SELECTED_INDEX, mSelectedIndex);

	    return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
	    if (state instanceof Bundle) {
	        Bundle bundle = (Bundle)state;

	        super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER_CLASS));
	        setSelectedIndex(bundle.getInt(STATE_SELECTED_INDEX));
	    } 
	    else
	       super.onRestoreInstanceState(state);   
	}
	
	@Override
	protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
	    // Make sure that the state of the child views in the side spinner are not saved since we 
		// handle the state in the onSaveInstanceState
	    super.dispatchFreezeSelfOnly(container);
	}

	@Override
	protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
	    // Make sure that the state of the child views in the side spinner are not restored since we 
		// handle the state in the onSaveInstanceState
	    super.dispatchThawSelfOnly(container);
	}
}