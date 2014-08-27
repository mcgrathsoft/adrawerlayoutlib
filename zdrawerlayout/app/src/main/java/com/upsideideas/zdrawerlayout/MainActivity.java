package com.upsideideas.zdrawerlayout;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public String[] mMenuStrings;
    private ListView menuList;
    private ZDrawerLayout drawerLayout;
    private TextView textSourceCodeLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (ZDrawerLayout) findViewById(R.id.zdrawerlayout);
        ZDrawerLayout.AnimationParameters params = drawerLayout.getAnimationParameters(ZDrawerLayout.LEFT_DRAWER);
        params.mScale = .25f;
        params.mAlpha = .95f;

        menuList = (ListView) drawerLayout.findViewById(R.id.menulist);

        // Setup an adapter for the menu list item strings
        String menuItemStrings[] = { "Item 1", "Item 2", "Item 3", "Item 4", "Exit"};
        menuList.setAdapter(new ArrayAdapter<String>(this, R.layout.menu_listitem_layout, menuItemStrings));
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                switch(arg2) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        Toast.makeText(getApplicationContext(), "Menu item "+(arg2+1)+" selected", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        finish();
                }
            }

        });

        // make the textview for source code link clickable
        textSourceCodeLink = (TextView) findViewById(R.id.textSourceCodeLink);
        textSourceCodeLink.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
