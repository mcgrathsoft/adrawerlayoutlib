/*
 * ZDrawerLayout - Created by extending doubleTwists' ADrawerLayout
 * See the copyright and license information in ADrawerLayout.java
 *
 * By Edward McGrath - edward@edwardmcgrath.com
 *
 * Here I extend ADrawerLayout in order to accomplish a specific animation effect
 * where the menu items appear behind the content and grow as you scroll open the drawer.
 *
 * Note: I had to also make a few changes to ADrawerLayout.java (change some stuff
 * from private to protected).
 *
 */

package com.upsideideas.zdrawerlayout;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;

public class ZDrawerLayout extends ADrawerLayout {

	public ZDrawerLayout(Context ctx, AttributeSet attrs, int defStyle) {
		super(ctx, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public ZDrawerLayout(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		// TODO Auto-generated constructor stub
	}

	public ZDrawerLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	protected void adjustScrollStates(float x, float y) {
		dx = Math.round(x);
		dy = Math.round(y);

		if (mLeft != null) {
			dxx = Math.round(dx * mContentParalaxFactorX);
			dyy = Math.round(dy * mContentParalaxFactorY);
			setDXYCompat(mLeft, dxx, dyy);

			animateLeft = mAnimateScrolling[0]
					&& mDrawerState.mActiveDrawer == LEFT_DRAWER;
			animateTop = mAnimateScrolling[1]
					&& mDrawerState.mActiveDrawer == TOP_DRAWER;
			animateRight = mAnimateScrolling[2]
					&& mDrawerState.mActiveDrawer == RIGHT_DRAWER;
			animateBottom = mAnimateScrolling[3]
					&& mDrawerState.mActiveDrawer == BOTTOM_DRAWER;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
				if (animateLeft)
					aparams = mAnimationParams[0];
				else if (animateTop)
					aparams = mAnimationParams[1];
				else if (animateRight)
					aparams = mAnimationParams[2];
				else if (animateBottom)
					aparams = mAnimationParams[3];
				else
					aparams = mNoAnimationParameters;

				// animScale = 1 - aparams.mScale * getScrollFraction();
				// animAlpha = 1 - aparams.mAlpha * getScrollFraction();
				// EM: scale according to the scroll fraction
				animScale = (1f - aparams.mScale)
						+ (aparams.mScale * getScrollFraction());
				// Log.d(TAG,
				// "scale="+animScale+"; scrollFraction="+getScrollFraction());
				animAlpha = (1f - aparams.mAlpha)
						+ (aparams.mAlpha * getScrollFraction());

				animRotX = -90.f * aparams.mRotX * getScrollFraction();
				animRotY = -90.f * aparams.mRotY * getScrollFraction();

				/*
				 * EM: changing which view we do the animation on so that we
				 * animate the menu (mLeft) instead of animating the content
				 * (mContent)
				 */

				// if (mContent.getScaleX() != animScale)
				// mContent.setScaleX(animScale);
				// if (mContent.getScaleY() != animScale)
				// mContent.setScaleY(animScale);
				// if (mContent.getAlpha() != animAlpha)
				// mContent.setAlpha(animAlpha);
				// if (mContent.getRotationY() != animRotY)
				// mContent.setRotationY(animRotY);
				// if (mContent.getRotationX() != animRotX)
				// mContent.setRotationX(animRotX);
				if (mLeft.getScaleX() != animScale)
					mLeft.setScaleX(animScale);
				if (mLeft.getScaleY() != animScale)
					mLeft.setScaleY(animScale);
				if (mLeft.getAlpha() != animAlpha)
					mLeft.setAlpha(animAlpha);
				// if (mLeft.getRotationY() != animRotY)
				// mLeft.setRotationY(animRotY);
				// if (mLeft.getRotationX() != animRotX)
				// mLeft.setRotationX(animRotX);

			}
		}

		if (mContentDimmer != null) {
			dimLeft = mDimContent[0]
					&& mDrawerState.mActiveDrawer == LEFT_DRAWER;
			dimTop = mDimContent[1] && mDrawerState.mActiveDrawer == TOP_DRAWER;
			dimRight = mDimContent[2]
					&& mDrawerState.mActiveDrawer == RIGHT_DRAWER;
			dimBottom = mDimContent[3]
					&& mDrawerState.mActiveDrawer == BOTTOM_DRAWER;

			// dxx = Math.round(dx);
			// if( mDrawerState.mActiveDrawer == LEFT_DRAWER ) dxx +=
			// mPeekSize.left;
			// if( mDrawerState.mActiveDrawer == RIGHT_DRAWER ) dxx -=
			// mPeekSize.right;
			// dyy = Math.round(dy);
			// if( mDrawerState.mActiveDrawer == TOP_DRAWER ) dyy +=
			// mPeekSize.top;
			// if( mDrawerState.mActiveDrawer == BOTTOM_DRAWER ) dyy -=
			// mPeekSize.bottom;

			if (isClosed() || dimLeft || dimTop || dimRight || dimBottom) {
				// dxx = Math.round(dx * mContentParalaxFactorX);
				// dyy = Math.round(dy * mContentParalaxFactorY);
				dxx = dx;
				dyy = dy;
				if (mPeekSize != null) {
					if (dimLeft)
						dxx += mPeekSize.left;
					if (dimRight)
						dxx -= mPeekSize.right;
					if (dimTop)
						dyy += mPeekSize.top;
					if (dimBottom)
						dyy -= mPeekSize.bottom;
				}
				setDXYCompat(mContentDimmer, dxx, dyy);
				// mContentDimmer.setScaleX(animScale);
				// mContentDimmer.setScaleY(animScale);

				// alpha = (1f - getScrollFraction());
				alpha = 0f;
				alpha = Math.min(1.f, Math.max(0.f, alpha));
				mContentDimmer.setBackgroundColor(Color.argb(
						Math.round(alpha * Color.alpha(mDimmingColor)),
						Color.red(mDimmingColor), Color.green(mDimmingColor),
						Color.blue(mDimmingColor)));
			}
		}

		if (mContent != null) {
			// dxx = -mLeft.getMeasuredWidth() + Math.max(0, dx);
			dyy = dy;
			// setDXYCompat(mLeft, dxx, dyy);

			// EM: translate content view over at the same time
			// so we have our content scroll to the right as the
			// menu scrolls onto the screen
			setDXYCompat(mContent, dx, dyy);

		}

		if (mRight != null) {
			dxx = mRight.getMeasuredWidth() + Math.min(0, dx);
			dyy = dy;
			setDXYCompat(mRight, dxx, dyy);
		}

		if (mTop != null) {
			dxx = dx;
			dyy = -mTop.getMeasuredHeight() + Math.max(0, dy);
			setDXYCompat(mTop, dxx, dyy);
		}

		if (mBottom != null) {
			dxx = dx;
			dyy = mBottom.getMeasuredHeight() + Math.min(0, dy);
			setDXYCompat(mBottom, dxx, dyy);
		}
	}

     /*
      * The implementation in ADrawerLayout assumes that the ScrollView is the first
      * element in the layout (mContent = getChildAt(0)).  But we want it to be after
      * the menu items (the listview) so that the draw order draws the content on top
      * of the menu items.
      */
	private void readViews() {
		mContent = null;
		for (int i = 0, count = getChildCount(); i < count; i++) {
			View v = getChildAt(i);
            /*
             * we assume here that our layout only contains a single ScrollView
             * and that ScrollView is our content.
             */
            if(v instanceof ScrollView) {
				// there should only be one of ScrollView in our ADrawerLayout
				mContent = v;
				continue;
			}

			//if (v.getId() == DIMMER_VIEW_ID)
			//	continue;

			LayoutParams params = (LayoutParams) v.getLayoutParams();
			switch (params.gravity) {
			case Gravity.LEFT: // left
				mLeft = v;
				mDrawerState.mLeftEnabled = true;
				break;
			case Gravity.RIGHT: // right
				mRight = v;
				mDrawerState.mRightEnabled = true;
				break;
			case Gravity.TOP: // top
				mTop = v;
				mDrawerState.mTopEnabled = true;
				break;
			case Gravity.BOTTOM: // bottom
				mBottom = v;
				mDrawerState.mBottomEnabled = true;
				break;
			default:
				mLeft = v;
				break;
			}
		}
	}

	@Override
	protected void onFinishInflate() {
		//super.onFinishInflate();

		readViews();
		// addContentDimmer();
	}

}
