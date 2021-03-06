package com.wisesharksoftware.panels.okcancel;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.smsbackupandroid.lib.R;
import com.wisesharksoftware.panels.IPanel;
import com.wisesharksoftware.panels.PanelManager;
import com.wisesharksoftware.panels.Structure;
import com.wisesharksoftware.panels.bars.BarTypes;
import com.wisesharksoftware.panels.okcancel.OKCancelPanel.OnLockedButtonListener;

public class SlidersBarsPanel extends OKCancelPanel {

	private LinearLayout barsContainer1;
	private LinearLayout barsContainer2;
	private TextView tvContainer1Caption;
	private TextView tvContainer2Caption;

	private int bars_count;
	private int[] progress;
	private int[] progressSavedValues;
	private int[] max;
	private int[] types;
	private String[] captions;
	private String[] columnCaptions;
	private SeekBar[] seekbars;
	private IOkCancelListener listener;

	@Override
	public void setListener(IOkCancelListener listener) {
		this.listener = listener;
	}

	public interface OnBarStopChangeListener {
		public void onStopChange(int barId, BarTypes barType, int change);
	}
	
	public SlidersBarsPanel(Context context) {
		this(context, null, 0);

	}

	

	public SlidersBarsPanel(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlidersBarsPanel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void findViews() {
		barsContainer1 = (LinearLayout) findViewById(R.id.bars_container_1_column);
		barsContainer2 = (LinearLayout) findViewById(R.id.bars_container_2_column);
		tvContainer1Caption = (TextView) findViewById(R.id.tvContainer1Caption);
		tvContainer2Caption = (TextView) findViewById(R.id.tvContainer2Caption);
	}

	@Override
	public void showPanel(IPanel hidePanel) {

		super.showPanel(hidePanel);
		// for (int i = 0; i < barsContainer1.getChildCount(); i++) {
		// View v = barsContainer1.getChildAt(i).findViewById(R.id.seekBar);
		// if (v != null && v instanceof SeekBar) {
		// ((SeekBar) v).setProgress(((SeekBar) v).getMax());
		// }
		// }
		// for (int i = 0; i < barsContainer2.getChildCount(); i++) {
		// View v = barsContainer2.getChildAt(i).findViewById(R.id.seekBar);
		// if (v != null && v instanceof SeekBar) {
		// ((SeekBar) v).setProgress(((SeekBar) v).getMax());
		// }
		// }
		if (listener != null) {
			listener.onShow();
		}

		invalidate();
	}

	@Override
	public void hidePanel() {
		super.hidePanel();
	}

	public void setToDefaultValues() {
		for (int i = 0; i < bars_count; i++) {
			int type = types[i];
			switch (type) {
			case 0:
				if (seekbars[i] != null) {
					seekbars[i].setProgress(progress[i]);
				}
				break;
			case 1:
				break;
			default:
				break;
			}
		}
	}

	public void setToMinValues() {
		for (int i = 0; i < bars_count; i++) {
			int type = types[i];
			switch (type) {
			case 0:
				if (seekbars[i] != null) {
					seekbars[i].setProgress(0);
				}
				break;
			case 1:
				break;
			default:
				break;
			}
		}
	}

	public void setEnabled(boolean enabled) {
		for (int i = 0; i < bars_count; i++) {
			int type = types[i];
			switch (type) {
			case 0:
				if (seekbars[i] != null) {
					seekbars[i].setEnabled(enabled);
				}
				break;
			case 1:
				break;
			default:
				break;
			}
		}
	}

	public void setToSavedValues() {
		for (int i = 0; i < bars_count; i++) {
			int type = types[i];
			switch (type) {
			case 0:
				if (seekbars[i] != null) {
					seekbars[i].setProgress(progressSavedValues[i]);
				}
				break;
			case 1:
				break;
			default:
				break;
			}
		}
	}

	public void setToValues(int[] progressValues) {
		for (int i = 0; i < bars_count; i++) {
			int type = types[i];
			switch (type) {
			case 0:
				if (seekbars[i] != null) {
					seekbars[i].setProgress(progressValues[i]);
				}
				break;
			case 1:
				break;
			default:
				break;
			}
		}
	}
	
	public void saveValues() {
		for (int i = 0; i < bars_count; i++) {
			int type = types[i];
			switch (type) {
			case 0:
				if (seekbars[i] != null) {
					progressSavedValues[i] = seekbars[i].getProgress();
				}
				break;
			case 1:
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void enableViews() {
		for (int i = 0; i < bars_count; i++) {
			int type = types[i];
			switch (type) {
			case 0:
				if (seekbars[i] != null) {
					seekbars[i].setEnabled(true);
				}
				break;
			default:
				break;
			}
		}
		enableViews = true;
	}

	@Override
	public void disableViews() {
		for (int i = 0; i < bars_count; i++) {
			int type = types[i];
			switch (type) {
			case 0:
				if (seekbars[i] != null) {
					seekbars[i].setEnabled(false);
				}
				break;
			default:
				break;
			}
		}
		enableViews = false;
	}

	@Override
	public boolean isViewsEnabled() {
		return enableViews;
	}

	@Override
	public void restoreOriginal() {
		setToMinValues();
	}

	@Override
	public void restoreOriginal(boolean sendChanges) {
		setEnabled(false);
		setToMinValues();
		setEnabled(true);
		if (listener != null) {
			listener.onRestore();
		}
	}

	@Override
	public void setStructure(Structure structure) {
		this.structure = structure;

		if (structure != null) {
			LayoutInflater.from(context).inflate(R.layout.panel_sliders_bars,
					this);
			findViews();
			if ((getPanelInfo().getProductIds())
					.size() != 0) {
				locked = true;
			}

			bars_count = ((OkCancelBarsPanelInfo) getPanelInfo()).getBarCount();
			progress = ((OkCancelBarsPanelInfo) getPanelInfo())
					.getBarProgress();
			progressSavedValues = ((OkCancelBarsPanelInfo) getPanelInfo())
					.getBarProgress();
			max = ((OkCancelBarsPanelInfo) getPanelInfo()).getBarMax();
			types = ((OkCancelBarsPanelInfo) getPanelInfo()).getBarTypes();
			captions = ((OkCancelBarsPanelInfo) getPanelInfo())
					.getBarCaptions();
			columnCaptions = ((OkCancelBarsPanelInfo) getPanelInfo())
					.getColumnCaptions();
			seekbars = new SeekBar[bars_count];

			if (columnCaptions != null) {
				if (columnCaptions.length == 2) {
					tvContainer1Caption.setText(columnCaptions[0]);
					tvContainer2Caption.setText(columnCaptions[1]);
					String fontPath1 = (String) tvContainer1Caption.getTag();
					if ((fontPath1 != null) && (fontPath1 != "")) {
						Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), fontPath1);
						tvContainer1Caption.setTypeface(myTypeface);
					}
					String fontPath2 = (String) tvContainer2Caption.getTag();
					if ((fontPath2 != null) && (fontPath2 != "")) {
						Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), fontPath2);
						tvContainer2Caption.setTypeface(myTypeface);
					}					
				}
			}

			if (bars_count != 0) {
				if (progress != null && progress.length == bars_count
						&& max != null && max.length == bars_count) {
					for (int i = 0; i < bars_count; i++) {
						int type = types[i];
						switch (type) {
						case 0:
							RelativeLayout main = (RelativeLayout) View
									.inflate(context, R.layout.panel_slider,
											null);
							TextView name = (TextView) main
									.findViewById(R.id.tvLabel);
							if (captions == null) {
								name.setText("Opacity");
							} else {
								name.setText(captions[i]);
							}
							SeekBar seekBar = (SeekBar) main
									.findViewById(R.id.seekBar);
							seekBar.setMax(max[i]);
							seekBar.setProgress(progress[i]);
							seekBar.setTag(i);
							seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

								@Override
								public void onStopTrackingTouch(SeekBar seekBar) {
									if (listener != null) {
										listener.onStop(
												seekBar.getTag(),
												BarTypes.OPACITY,
												seekBar.getProgress());
									}
								}

								@Override
								public void onStartTrackingTouch(SeekBar seekBar) {

								}

								@Override
								public void onProgressChanged(SeekBar seekBar,
										int progress, boolean fromUser) {
									if (listener != null) {
										listener.onChange(
												seekBar.getTag(),
												BarTypes.OPACITY, progress);
									}
									if ((fromUser) && (listener != null)) {
										listener.onChangeFromUser(seekBar.getTag(), BarTypes.OPACITY, progress);
									}
								}
							});
							seekbars[i] = seekBar;
							if (i < bars_count / 2) {
								barsContainer1.addView(main);								
							} else {
								barsContainer2.addView(main);
							}
							break;
						default:
							break;
						}

					}
				}
			}
		}
	}

	@Override
	public void unlockAll() {
		locked = false;
	}

	@Override
	public void unlockByProductId(String productId) {
		List<String> productIds = (getPanelInfo()
				.getProductIds());

		for (int i = 0; i < productIds.size(); i++) {
			if (productIds.get(i).equals(productId)) {
				locked = false;
			}
		}
	}

	public List<String> getProductIds() {
		return (getPanelInfo().getProductIds());
	}

	@Override
	public String getPanelType() {
		return PanelManager.SLIDERS_BAR_PANEL_TYPE;
	}

	public boolean isLocked() {
		return locked;
	}
}
