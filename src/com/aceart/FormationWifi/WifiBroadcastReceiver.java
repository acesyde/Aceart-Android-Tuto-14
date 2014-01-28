package com.aceart.FormationWifi;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class WifiBroadcastReceiver extends BroadcastReceiver {

	private WifiManager wifiManager;
	private WifiAdapter wifiAdapter;
	private List<WifiItem> listeWifiItem;

	@Override
	public void onReceive(Context context, Intent intent) {
		wifiManager = ((FormationWifiActivity) context).getCurrentWifiManager();
		wifiAdapter = ((FormationWifiActivity) context).getWifiAdapter();
		listeWifiItem = ((FormationWifiActivity) context).getListeWifiItem();

		// On vérifie que notre objet est bien instancié
		if (wifiManager != null) {

			// On vérifie que le wifi est allumé
			if (wifiManager.isWifiEnabled()) {
				// On récupère les scans
				List<ScanResult> listeScan = wifiManager.getScanResults();

				// On vide notre liste
				listeWifiItem.clear();

				// Pour chaque scan
				for (ScanResult scanResult : listeScan) {
					WifiItem item = new WifiItem();

					item.setAdresseMac(scanResult.BSSID);
					item.setAPName(scanResult.SSID);
					item.setForceSignal(scanResult.level);

					Log.d("FormationWifi", scanResult.SSID + " LEVEL "
							+ scanResult.level);

					listeWifiItem.add(item);
				}

				// On rafraichit la liste
				wifiAdapter.notifyDataSetChanged();
			} else {
				Toast.makeText(context, "Vous devez activer votre wifi",
						Toast.LENGTH_SHORT);
			}
		}

	}

}
