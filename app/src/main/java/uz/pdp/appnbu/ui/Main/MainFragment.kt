package uz.pdp.appnbu.ui.Main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import uz.pdp.appnbu.Adapters.VpAdapter
import uz.pdp.appnbu.R
import uz.pdp.appnbu.databinding.FragmentMainBinding
import uz.pdp.appnbu.fragments.AllCurrenciesFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
lateinit var binding:FragmentMainBinding
lateinit var vpAdapter: VpAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMainBinding.inflate(inflater,container,false)

        binding.bottomview.itemIconTintList=null

           vpAdapter= VpAdapter(this,object :AllCurrenciesFragment.Opencalcpage{
               override fun oncalcpage() {
                   binding.vp.currentItem=2
               }

           })
        binding.vp.adapter=vpAdapter

        binding.vp.isUserInputEnabled=false


        binding.vp.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                when(position){
                    0->binding.bottomview.selectedItemId=R.id.mainWindowFragment
                    1->binding.bottomview.selectedItemId=R.id.allCurrenciesFragment
                    2->binding.bottomview.selectedItemId=R.id.calculatorFragment
                }
            }
        })
        binding.bottomview.setOnItemSelectedListener(object :NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val id=item.itemId
                when(id){
                    R.id.mainWindowFragment->binding.vp.currentItem=0
                    R.id.allCurrenciesFragment->binding.vp.currentItem=1
                    R.id.calculatorFragment->binding.vp.currentItem=2

                }
                return true
            }

        })






        return binding.root
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}