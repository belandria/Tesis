package com.trabajo.t1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

public class Usuario extends Activity {
	public Button botonConectar, botonAbrirCerrar;
	private static String MAC = null;
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private String mConnectedDeviceName = null;
	public static final boolean DEBUG = true;
	public BluetoothAdapter BA;
	public BluetoothChatService mBluetoothChatService = null;
	public int mControlKeyCode;
	private static TextView mTitle;
	public TextView respuesta;
	public int i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ventanauser);
		
		botonAbrirCerrar = (Button) findViewById(R.id.bAbrirCerrar);
				respuesta = (TextView) findViewById(R.id.txtMsjRecibido);
		mTitle = (TextView) findViewById(R.id.txtNombreMod);
		BA = BluetoothAdapter.getDefaultAdapter();
		mBluetoothChatService = new BluetoothChatService(this, mHandler);
		mBluetoothChatService.start();
		i=1;
	}


	public void abrircerrar(View v) {
		if(i==1)
		{
			write('a');
			i=2;
		}
		if(i==2)
		{
			write('c');
			i=1;
		}
		
	}

	private void write(char c) {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[1];
		buffer[0] = (byte) c;
		mBluetoothChatService.write(buffer);

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (DEBUG)
			switch (requestCode) {
			case REQUEST_CONNECT_DEVICE:
				if (resultCode == Activity.RESULT_OK)
					; {
				MAC = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				BluetoothDevice device = BA.getRemoteDevice(MAC);
				mBluetoothChatService.connect(device, true);
			}
				break;
			}
	}

	private final Handler mHandler = new Handler() 
	{
		
		public void handlerMessage(Message msg) {
			switch (msg.what) {
			case Constants.MESSAGE_STATE_CHANGE:
				switch (msg.arg1) {
				case BluetoothChatService.STATE_CONNECTED:
					mTitle.setText("Conectado a: " + mConnectedDeviceName);
					break;
				case BluetoothChatService.STATE_CONNECTING:
					mTitle.setText("Conectando...");
					break;
				case BluetoothChatService.STATE_LISTEN:
				case BluetoothChatService.STATE_NONE:
					mTitle.setText("No Conectado");
					break;
				}
				break;
			case Constants.MESSAGE_READ:
				byte[] data = (byte[]) msg.obj;
				int[] ints = new int[data.length];
				for (int i = 0; i < data.length; i++) {
					ints[i] = (int) data[i];
				}
				int valor = ints[0];
				switch (valor) {
				case 97:
					respuesta.setText("Valor enviado1");
					break;
				case 98:
					respuesta.setText("Valor enviado2");
					break;
				case 99:
					respuesta.setText("Valor enviado3");
					break;
				case 100:
					respuesta.setText("Velocidad ajustada");
					break;
				default:
					respuesta.setText("Error");
					break;
				}
				break;

			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    switch (item.getItemId()) 
	    {
	        case R.id.action_settings: 
	        {
	            // Launch the DeviceListActivity to see devices and do scan
	        	Intent serverIntent = new Intent(this, DeviceListActivity.class);
	            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
	            return true;
	        }
	    }
	    return false;
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		respuesta.setText("");
		mBluetoothChatService.stop();
		finish();
		Toast.makeText(getApplicationContext(),"Fin de la actividad",
		Toast.LENGTH_SHORT).show();	
	}
}
