package com.example.bmilearningproject.util;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.bmilearningproject.R;

public class MyScaleView extends View {
    static int screenSize = 480;
    static private float pxmm = screenSize / 67.f;
    int width, height, midScreenPoint;
    float startingPoint = 0;
    float downpoint = 0, movablePoint = 0, downPointClone = 0;
    private float mainPoint = 0, mainPointClone = 0;
    boolean isDown = false;
    boolean isUpward = false;
    private boolean isMove;
    private onViewUpdateListener mListener;
    private Paint gradientPaint;
    private float rulersize = 0;
    private Paint rulerPaint, textPaint, goldenPaint;
    private int endPoint;
    boolean isSizeChanged = false;
    float userStartingPoint = 0f;
    private int scaleLineSmall;
    private int scaleLineMedium;
    private int scaleLineLarge;
    private int textStartPoint;
    private int yellowLineStrokeWidth;
    boolean isFirstTime = true;

    public MyScaleView(Context context, AttributeSet foo) {
        super(context, foo);
        if (!isInEditMode()) {
            init(context);
        }
    }

    private void init(Context context) {
        yellowLineStrokeWidth = (int) getResources().getDimension(R.dimen.yellow_line_stroke_width);
        gradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rulersize = pxmm * 10;
        rulerPaint = new Paint();
        rulerPaint.setStyle(Paint.Style.STROKE);
        rulerPaint.setStrokeWidth(1);
        rulerPaint.setAntiAlias(false);
        rulerPaint.setColor(getResources().getColor(R.color.common_color));
        textPaint = new TextPaint();
//		Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/bebas_neue.ttf");
//		textPaint.setTypeface(typeface);
//		textPaint.setStyle(Paint.Style.STROKE);
//		textPaint.setStrokeWidth(0);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(getResources().getDimension(R.dimen.txt_size));
        textPaint.setColor(context.getResources().getColor(R.color.green));
        goldenPaint = new Paint();
        goldenPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        goldenPaint.setColor(context.getResources().getColor(R.color.green));
        goldenPaint.setStrokeWidth(yellowLineStrokeWidth);
        goldenPaint.setStrokeJoin(Paint.Join.ROUND);
        goldenPaint.setStrokeCap(Paint.Cap.ROUND);
        goldenPaint.setPathEffect(new CornerPathEffect(10));
        goldenPaint.setAntiAlias(true);
        scaleLineSmall = (int) getResources().getDimension(R.dimen.scale_line_small);
        scaleLineMedium = (int) getResources().getDimension(R.dimen.scale_line_medium);
        scaleLineLarge = (int) getResources().getDimension(R.dimen.scale_line_large);
        textStartPoint = (int) getResources().getDimension(R.dimen.text_start_point);
    }

    public void setUpdateListener(onViewUpdateListener onViewUpdateListener) {
        mListener = onViewUpdateListener;
    }

//	@Override
//	public void onSizeChanged(int w, int h, int oldW, int oldH) {
//		width = w;
//		height = h;
//		screenSize = height;
//		pxmm = screenSize / 67.f;
//		midScreenPoint = height / 2;
//		endPoint = width - 40;
//		if (isSizeChanged) {
//			isSizeChanged = false;
//			mainPoint = midScreenPoint - (userStartingPoint * 10 * pxmm);
//		}
//		gradientPaint.setShader(new LinearGradient(0, 0, width, rulersize, getResources().getColor(R.color.transparent),
//				getResources().getColor(R.color.transparent), android.graphics.Shader.TileMode.MIRROR));
//	}

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        width = w;
        height = h;
        screenSize = height;
        pxmm = screenSize / 67.f;
        midScreenPoint = height / 2;
        endPoint = width - 40;

        // If user previously requested a starting point before size was known,
        // apply it now. Otherwise make sure mainPoint is consistent.
        if (isSizeChanged) {
            isSizeChanged = false;
            // userStartingPoint is in "units" (cm or ft * 10 etc in your logic)
            mainPoint = midScreenPoint - (userStartingPoint * 10f * pxmm);
        } else {
            // ensure mainPoint is initialized (avoid NaN)
            // keep existing mainPoint if set; otherwise default 0
            if (Float.isNaN(mainPoint)) mainPoint = 0f;
        }

        gradientPaint.setShader(new LinearGradient(0, 0, width, rulersize,
                getResources().getColor(R.color.transparent),
                getResources().getColor(R.color.transparent),
                android.graphics.Shader.TileMode.MIRROR));
    }


    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawRect(0f, midScreenPoint - (rulersize / 2), width, midScreenPoint + (rulersize / 2), gradientPaint);
        startingPoint = mainPoint;
        for (int i = 1;; ++i) {
            if (startingPoint > screenSize) {
                break;
            }
            startingPoint = startingPoint + pxmm;
            int size = (i % 10 == 0) ? scaleLineLarge : (i % 5 == 0) ? scaleLineMedium : scaleLineSmall;
            canvas.drawLine(endPoint - size, startingPoint, endPoint, startingPoint, rulerPaint);
            if (i % 10 == 0) {
                System.out.println("done   " + i);
//				canvas.drawText((i / 10) + " cm", endPoint - textStartPoint, startingPoint + 8, textPaint);
                canvas.drawText((i / 10) + "", endPoint - textStartPoint, startingPoint + 8, textPaint);
            }
        }
        canvas.drawLine(0f, midScreenPoint, width - 20, midScreenPoint, goldenPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Process touch and update mainPoint correctly.
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isMove = true;
                isDown = false;
                isUpward = false;
                downpoint = event.getY();
                downPointClone = event.getY();
                return true;

            case MotionEvent.ACTION_MOVE:
                movablePoint = event.getY();

                // if user moved upwards (finger Y decreased) -> we move scale upward
                if (downPointClone > movablePoint) {
                    if (isUpward) {
                        downpoint = event.getY();
                        downPointClone = downpoint;
                    }
                    isDown = true;
                    isUpward = false;

                    if (downPointClone - movablePoint > 1) {
                        float delta = (downPointClone - movablePoint);
                        mainPoint -= delta; // moving upward => decrease mainPoint (move content up)
                        downPointClone = movablePoint;

                        // optional clamp lower bound so you can't scroll infinitely
                        // e.g., prevent mainPoint < -maxRange (choose a reasonable maxRange)
                        invalidate();
                    }

                } else {
                    // moved downward (finger Y increased) -> move scale downward
                    if (isMove) {
                        if (isDown) {
                            downpoint = event.getY();
                            downPointClone = downpoint;
                        }
                        isDown = false;
                        isUpward = true;

                        if (movablePoint - downPointClone > 1) {
                            float delta = (movablePoint - downPointClone);
                            mainPoint += delta; // moving down => increase mainPoint (move content down)
                            downPointClone = movablePoint;

                            // Prevent scrolling past top
                            if (mainPoint > 0f) {
                                mainPoint = 0f;
                                isMove = false;
                            }

                            invalidate();
                        }
                    }
                }

                // REPORT the value after we've updated mainPoint:
                if (mListener != null) {
                    // IMPORTANT: correct conversion from mainPoint -> units (cm)
                    // clickPoint = distance (pixels) from midScreenPoint divided by pxmm per unit
                    float clickPoint = (midScreenPoint - mainPoint) / (pxmm * 10f);
                    mListener.onViewUpdate(clickPoint);
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // final report on up
                if (mListener != null) {
                    float clickPoint = (midScreenPoint - mainPoint) / (pxmm * 10f);
                    mListener.onViewUpdate(clickPoint);
                }
                return true;

            default:
                return super.onTouchEvent(event);
        }
    }

//
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		System.out.println("touch event fire");
//		mainPointClone = mainPoint;
//		if (mainPoint < 0) {
//			mainPointClone = -mainPoint;
//		}
////		float clickPoint = ((midScreenPoint + mainPointClone) / (pxmm * 10));
//		float clickPoint = (midScreenPoint - mainPoint) / (pxmm * 10);
//
//		if (mListener != null) {
    ////			mListener.onViewUpdate((midScreenPoint + mainPointClone) / (pxmm * 10));
//			mListener.onViewUpdate(clickPoint);
//
//		}
//		System.out.println("click point" + clickPoint + "");
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			isMove = true;
//			isDown = false;
//			isUpward = false;
//			downpoint = event.getY();
//			downPointClone = event.getY();
//			break;
//		case MotionEvent.ACTION_MOVE:
//			movablePoint = event.getY();
//			System.out.println("move");
//			if (downPointClone > movablePoint) {
//				/**
//				 * if user first starts moving downward and then upwards then
//				 * this method makes it to move upward
//				 */
//				if (isUpward) {
//					downpoint = event.getY();
//					downPointClone = downpoint;
//				}
//				isDown = true;
//				isUpward = false;
//				/**
//				 * make this differnce of 1, otherwise it moves very fast and
//				 * nothing shows clearly
//				 */
//				if (downPointClone - movablePoint > 1) {
//					mainPoint = mainPoint + (-(downPointClone - movablePoint));
//					downPointClone = movablePoint;
//					invalidate();
//				}
//			} else {
//				// downwards
//				if (isMove) {
//					/**
//					 * if user first starts moving upward and then downwards,
//					 * then this method makes it to move upward
//					 */
//					if (isDown) {
//						downpoint = event.getY();
//						downPointClone = downpoint;
//					}
//					isDown = false;
//					isUpward = true;
//					if (movablePoint - downpoint > 1) {
//						mainPoint = mainPoint + ((movablePoint - downPointClone));
//						downPointClone = movablePoint;
//						if (mainPoint > 0) {
//							mainPoint = 0;
//							isMove = false;
//						}
//						invalidate();
//					}
//				}
//			}
//			break;
//		case MotionEvent.ACTION_UP:
//			System.out.println("up");
//		default:
//			break;
//		}
//		return true;
//	}

//	public void setStartingPoint(float point) {
//		userStartingPoint = point;
//		isSizeChanged = true;
//		if (isFirstTime) {
//			isFirstTime = false;
//			if (mListener != null) {
//				mListener.onViewUpdate(point);
//			}
//		}
//	}

//	public void setStartingPoint(float point) {
//		userStartingPoint = point;
//		mainPoint = midScreenPoint - (userStartingPoint * 10 * pxmm);
//		invalidate(); // <-- Important!
//
//		if (mListener != null) {
//			mListener.onViewUpdate(point);
//		}
//	}

    public void setStartingPoint(float point) {
        // point = desired scale value in same units listener expects (e.g. cm)
        userStartingPoint = point;
        isSizeChanged = true;

        // If midScreenPoint is already known (view sized), update immediately.
        if (midScreenPoint > 0) {
            mainPoint = midScreenPoint - (userStartingPoint * 10f * pxmm);
            // clamp mainPoint so it doesn't produce nonsense (optional)
            // For example, prevent mainPoint being too far positive:
            if (mainPoint > 0f) mainPoint = 0f;
            invalidate();
        }

        // First-time callback: give initial value
        if (mListener != null) {
            mListener.onViewUpdate(point);
        }
    }
}
