package ui.fragments


import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast

import com.example.anonymous.cikgood.R
import com.example.anonymous.cikgood.config.ServerConfig
import com.example.anonymous.cikgood.models.DataMatpel
import com.example.anonymous.cikgood.models.Kabupaten
import com.example.anonymous.cikgood.models.Tingkatan
import com.example.anonymous.cikgood.response.ResponseCariGuru
import com.example.anonymous.cikgood.response.ResponseDataMatpel
import com.example.anonymous.cikgood.response.ResponseKabupaten
import com.example.anonymous.cikgood.response.ResponseTingkatan
import com.example.anonymous.cikgood.rests.ApiClient
import com.example.anonymous.cikgood.rests.ApiInterface
import com.example.anonymous.cikgood.utils.SessionManager
import com.smarteist.autoimageslider.SliderLayout
import com.smarteist.autoimageslider.SliderView

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ui.activities.CariGuruActivity
import ui.activities.GuruActivity


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val CvCars: CardView? = null
    private val CvBikes: CardView? = null
    private val CvBluebird: CardView? = null
    private val CvOthers: CardView? = null
    internal var spinnerTingkatan: Spinner
    internal var spinnerDataMatpel: Spinner
    internal var spinnerKabupaten: Spinner
    internal var tingkatan: Int = 0
    internal var matpel: Int = 0
    internal var kota: String? = null
    internal var btnCari: Button
    internal var sliderLayout: SliderLayout? = null
    internal var sessionManager: SessionManager? = null
    internal var apiService: ApiInterface? = null
    internal var loading: ProgressDialog? = null
    private var ivPaud: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View
        view = inflater.inflate(R.layout.fragment_home, container, false)

        //        sliderLayout = view.findViewById(R.id.imageSlider);
        //        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.THIN_WORM); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        //        sliderLayout.setScrollTimeInSec(2); //set scroll delay in seconds :
        spinnerTingkatan = view.findViewById<View>(R.id.spinnerTingkatan) as Spinner
        spinnerDataMatpel = view.findViewById<View>(R.id.spinnerMatpel) as Spinner
        spinnerKabupaten = view.findViewById<View>(R.id.spinnerKabupaten) as Spinner
        btnCari = view.findViewById<View>(R.id.btn_cari) as Button
        ivPaud = view.findViewById<View>(R.id.iv_paud) as ImageView

        ivPaud!!.setOnClickListener { startActivity(Intent(activity, CariGuruActivity::class.java)) }

        //        sliderLayout = view.findViewById(R.id.thumbnail);
        //        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.THIN_WORM); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        //        sliderLayout.setScrollTimeInSec(2); //set scroll delay in seconds :

        //        setSliderViews();
        // change color in primaryDark
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity!!.window.statusBarColor = resources.getColor(R.color.colorAccent)
        }
        // change color in primaryDark

        // change icon color status bar
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        // change icon color status bar

        return view
    }

    //    private void setSliderViews() {
    //
    //        for (int i = 0; i <= 3; i++) {
    //
    //            SliderView sliderView = new SliderView(getActivity());
    //
    //            switch (i) {
    //                case 0:
    //                    sliderView.setImageUrl("https://aa-catering.com/wp-content/uploads/2015/08/W5-e1499830148375.jpg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
    //                    break;
    //                case 1:
    //                    sliderView.setImageUrl("https://aa-catering.com/wp-content/uploads/2015/12/Combo-e1499830186232.jpg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
    //                    break;
    //                case 2:
    //                    sliderView.setImageUrl("https://aa-catering.com/wp-content/uploads/2015/12/BLUE-JELLY-1024x576.jpg?auto=compress&cs=tinysrgb&h=750&w=1260");
    //                    break;
    //                case 3:
    //                    sliderView.setImageUrl("https://aa-catering.com/wp-content/uploads/2015/12/Buffet3-1024x576.jpg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
    //                    break;
    //            }
    //
    //            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
    ////            sliderView.setDescription("setDescription " + (i + 1));
    //            final int finalI = i;
    //            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
    //                @Override
    //                public void onSliderClick(SliderView sliderView) {
    ////                    Toast.makeText(BerandaFragment.this, "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
    //                }
    //            });
    //
    //            //at last add this view in your layout :
    //            sliderLayout.addSliderView(sliderView);
    //        }
    //    }
    //
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }

    companion object {

        val TINGKATAN = "tingkatan"
        val MATPEL = "matpel"
        val KOTA = "kota"
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }


}// Required empty public constructor
