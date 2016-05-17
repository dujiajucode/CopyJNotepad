package net.dujiaju.pnotepad;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.dujiaju.pnotepad.fragment.ArticleListFragment;
import net.dujiaju.pnotepad.model.Folder;
import net.dujiaju.pnotepad.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private List<Folder> mFolderList;
    private MyPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setNavigationIcon(R.drawable.ic_menu_slide);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mFolderList = new ArrayList<>();
        mFolderList.add(new Folder(1, "第一个文件夹"));
        mFolderList.add(new Folder(2, "第二个文件夹"));
        mFolderList.add(new Folder(3, "第三个文件夹"));

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tablayout);
        assert slidingTabLayout != null;
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        assert viewPager != null;
        mPagerAdapter = new MyPagerAdapter(getFragmentManager());
        viewPager.setOffscreenPageLimit(mFolderList.size());
        viewPager.setAdapter(mPagerAdapter);
        slidingTabLayout.setViewPager(viewPager, calculateScreenX());
    }

    private int calculateScreenX() {
        return getResources().getDisplayMetrics().widthPixels;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new ArticleListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ArticleListFragment.ARTICLE_LIST_FRAGMENT_FOLDER, mFolderList.get(position).getID());
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFolderList.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return mFolderList == null ? 0 : mFolderList.size();
        }
    }

}
