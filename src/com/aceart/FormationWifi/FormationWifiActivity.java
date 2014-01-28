package com.aceart.FormationWifi;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class FormationWifiActivity extends Activity {

	private Button boutonRechercher;
	private ListView listeViewWifi;
	private List<WifiItem> listeWifiItem;
	private WifiAdapter wifiAdapter;
	private WifiManager wifiManager;
	private WifiBroadcastReceiver broadcastReceiver;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		listeViewWifi = (ListView) findViewById(R.id.listViewWifi);
		boutonRechercher = (Button) findViewById(R.id.buttonRefresh);
		
		boutonRechercher.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(wifiManager != null)
					wifiManager.startScan();				
			}
		});

		// On récupère le service Wifi d'Android
		wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);

		// Gestion de la liste des AP Wifi (Voir tuto sur les adapters et les
		// listview)
		listeWifiItem = new ArrayList<WifiItem>();
		wifiAdapter = new WifiAdapter(this, listeWifiItem);
		listeViewWifi.setAdapter(wifiAdapter);

		// Création du broadcast Receiver
		broadcastReceiver = new WifiBroadcastReceiver();

		// On attache le receiver au scan result
		registerReceiver(broadcastReceiver, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

	}

	// On arrête le receiver quand on met en pause l'application
	@Override
	protected void onPause() {
		unregisterReceiver(broadcastReceiver);
		super.onPause();
	}

	// On remet en rourte le receiver quand on reviens sur l'application
	@Override
	protected void onResume() {
		registerReceiver(broadcastReceiver, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		super.onResume();
	}

	public WifiManager getCurrentWifiManager() {
		return wifiManager;
	}
	
	public WifiAdapter getWifiAdapter() {
		return wifiAdapter;
	}
	
	public List<WifiItem> getListeWifiItem() {
		return listeWifiItem;
	}
}