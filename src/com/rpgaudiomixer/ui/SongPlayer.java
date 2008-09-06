package com.rpgaudiomixer.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scale;

public class SongPlayer extends Composite {

	private Scale songVolumeScale;

	public SongPlayer(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout());
		Composite controlComposite = new Composite(this, SWT.NULL);
		GridLayout controlLayout = new GridLayout();
		controlLayout.numColumns = 7;
		controlLayout.horizontalSpacing = 2;
		controlLayout.verticalSpacing = 2;
		controlLayout.marginHeight = 2;
		controlLayout.marginWidth = 2;
		controlComposite.setLayout(controlLayout);
		Button playButton = new Button(controlComposite, SWT.PUSH);
		playButton.setText("Play");
		
		Button stopButton = new Button(controlComposite, SWT.PUSH);
		stopButton.setText("Stop");
		stopButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				// TODO: Change the way this is implemented
				//stopSong();
			}
		});
		
		Button nextButton = new Button(controlComposite, SWT.PUSH);
		nextButton.setText("Next");
		nextButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				//nextSong();
			}
		});
		
		ProgressBar songProgressBar = new ProgressBar(controlComposite, SWT.SMOOTH);
		songProgressBar.setMinimum(0);
		songProgressBar.setMaximum(1000);
		songProgressBar.setSelection(0);
		
		songVolumeScale = new Scale(controlComposite, SWT.HORIZONTAL);
		songVolumeScale.setMinimum(0);
		songVolumeScale.setMaximum(100);
		songVolumeScale.setIncrement(1);
		songVolumeScale.setPageIncrement(10);
		songVolumeScale.setSelection(100);
		songVolumeScale.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				// TODO: Change the way this is implemented
				//audioEngine.setSongVolume(songVolumeScale.getSelection());
			}
		});
	}

}
