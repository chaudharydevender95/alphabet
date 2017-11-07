package com.example.wolfknight.alphabet;

import android.content.Intent;
import android.icu.util.ULocale;
import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class lesson extends AppCompatActivity implements View.OnClickListener{



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public lesson(SectionsPagerAdapter mSectionsPagerAdapter, ViewPager mViewPager) {
        this.mSectionsPagerAdapter = mSectionsPagerAdapter;
        this.mViewPager = mViewPager;
    }

    public lesson(SectionsPagerAdapter mSectionsPagerAdapter) {
        this.mSectionsPagerAdapter = mSectionsPagerAdapter;

    }

    public lesson(){

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

          /*familyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(lesson.this,"Clicked on button",Toast.LENGTH_SHORT).show();
            }
        });*/

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lesson, menu);
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


    @Override
    public void onClick(View v) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            reference.speakout(" ");
            reference.speakout(" ");

            final View rootView;
            if(category.category=="toddler") {

                if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                    rootView = inflater.inflate(R.layout.fragment_alphabetclass, container, false);

                    ImageView familyButton = (ImageView) rootView.findViewById(R.id.familyFragButton);
                    familyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), family.class));
                        }
                    });
                    ImageView bodyParts = (ImageView) rootView.findViewById(R.id.body_parts);
                    bodyParts.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "bodyParts", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), tod_bodyParts.class));
                        }
                    });

                    ImageView mustWords = (ImageView) rootView.findViewById(R.id.must_words);
                    mustWords.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), mustWords.class));
                        }
                    });

                    final ImageView rhymActivity = (ImageView) rootView.findViewById(R.id.rhymesActivity);
                    rhymActivity.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(),rhymsActivity.class));
                        }
                    });
                } else {
                    rootView = inflater.inflate(R.layout.fragment_alphabetpractice, container, false);
                    ImageView yourfamilyButton = (ImageView) rootView.findViewById(R.id.prac_familyFragButton);
                    yourfamilyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), tod_family_prac.class));
                        }
                    });

                    ImageView mustWordPrac = (ImageView) rootView.findViewById(R.id.mustWordsPrac);
                    mustWordPrac.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), mustWordPrac.class));
                        }
                    });
                    ImageView bodyPartsPrac = (ImageView) rootView.findViewById(R.id.prac_body_parts);
                    bodyPartsPrac.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), tod_bodyParts_prac.class));
                        }
                    });
                    ImageView thingsToEat = (ImageView) rootView.findViewById(R.id.thingsToEat);
                    thingsToEat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), tod_thingsToEat.class));
                        }
                    });

                }
                return rootView;
            }
            else {
                if(category.category=="baby") {
                    if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                        rootView = inflater.inflate(R.layout.fragment_baby_category__fragement, container, false);
                        ImageView bodyparts = (ImageView) rootView.findViewById(R.id.body_parts);
                        bodyparts.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(),baby_body_parts.class));
                            }
                        });

                        ImageView a_i = (ImageView) rootView.findViewById(R.id.A_I_But);
                        a_i.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(),baby_AI.class));
                            }
                        });
                        ImageView colors = (ImageView) rootView.findViewById(R.id.colorsActivity);
                       colors.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(),baby_color.class));
                            }
                        });
                    } else {
                        rootView = inflater.inflate(R.layout.fragment_baby_frag_prac, container, false);
                        ImageView bodyparts = (ImageView) rootView.findViewById(R.id.body_parts);
                        bodyparts.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(),baby_body_parts_prac.class));
                            }
                        });
                        ImageView a_i_draw = (ImageView) rootView.findViewById(R.id.A_I_But);
                        a_i_draw.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(),baby_a_i_prac.class));
                            }
                        });
                    }
                }
                else{
                    if(category.category=="infant"){
                        if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
                            rootView = inflater.inflate(R.layout.fragment_infant, container, false);
                        }
                        else{
                            rootView = inflater.inflate(R.layout.fragment_infant_prac, container, false);

                            ImageView smallWords = (ImageView) rootView.findViewById(R.id.infant_smallWord);
                            smallWords.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getContext(),infant_missing_letter.class));
                                }
                            });
                        }

                    }else{
                        if(category.category=="kiddie"){
                            if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
                                rootView = inflater.inflate(R.layout.fragment_kiddle_prac, container, false);
                            }
                            else {
                                rootView = inflater.inflate(R.layout.fragment_kiddle_prac, container, false);
                                ImageView fillup = (ImageView) rootView.findViewById(R.id.kiddle_fillup);
                                fillup.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(getContext(),kiddleFillupPrac.class));
                                    }
                                });
                                ImageView kiddlequiz = (ImageView)rootView.findViewById(R.id.kiddleMulDiv);
                                kiddlequiz.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(getContext(),kiddleMulDiv.class));
                                    }
                                });
                            }
                        }
                        else
                        //rootView = inflater.inflate(R.layout.fragment_infant, container, false);
                        rootView=null;
                    }


                }
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Class";
                case 1:
                    return "Practice";
            }
            return null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
