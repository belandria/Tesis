package com.trabajo.t1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Principal extends Activity {

	
	public Button userP,admP;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		userP = (Button) findViewById (R.id.botonUser);
		admP = (Button) findViewById (R.id.botonAdm);
		userP.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Principal.this, Usuario.class);
				startActivity(intent);
			}
		});
		admP.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Principal.this, Administrador.class);
				startActivity(intent);
			}
		});
		
	}

	
	
	
}
