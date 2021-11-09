package uz.pdp.appnbu.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import androidx.work.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uz.pdp.appnbu.Adapters.HistoryRvAdapter
import uz.pdp.appnbu.Adapters.MiniVpAdater
import uz.pdp.appnbu.R
import uz.pdp.appnbu.Utils.MySharedPreference
import uz.pdp.appnbu.models.CurrencyModel
import uz.pdp.appnbu.ViewModel.MyViewModel
import uz.pdp.appnbu.WorkManager.MyWorker
import uz.pdp.appnbu.databinding.ActivityMainBinding
import uz.pdp.appnbu.databinding.FragmentMainWindowBinding
import uz.pdp.appnbu.databinding.ItemChangesHistoryRvBinding
import uz.pdp.appnbu.databinding.ItemTablayoutBinding
import uz.pdp.appnbu.room.Database.AppDatabase
import uz.pdp.appnbu.room.entities.HistoryModel
import java.util.concurrent.TimeUnit

class MainWindowFragment : Fragment() {

    lateinit var binding:FragmentMainWindowBinding
    lateinit var myViewModel: MyViewModel
    lateinit var miniVpAdater: MiniVpAdater
    lateinit var list:ArrayList<CurrencyModel>
    lateinit var workManager: WorkManager
    lateinit var appDatabase: AppDatabase
    lateinit var historyRvAdapter: HistoryRvAdapter
    lateinit var mySharedPreference: MySharedPreference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().actionBar?.elevation=0f
        binding= FragmentMainWindowBinding.inflate(inflater,container,false)
        myViewModel=ViewModelProvider(this)[MyViewModel::class.java]
        workManager= WorkManager.getInstance(requireContext())
        startworkmanager()
        appDatabase= AppDatabase.getInstance(requireContext())
        mySharedPreference=MySharedPreference.getinstance(requireContext())
        setdefaultShared()
        list= ArrayList()
        miniVpAdater=MiniVpAdater(list)
        myViewModel.getretrofitlist(requireContext()).observe(viewLifecycleOwner, Observer{
                   list.addAll(it.filter { response-> response.nbu_buy_price!=""
                   }.reversed())

            addhistoryinformation(it)

            miniVpAdater.notifyDataSetChanged()
            var historylist=loadhistorylist(list[0].code)
            historyRvAdapter= HistoryRvAdapter(historylist)
            binding.recyclerView.adapter=historyRvAdapter

            TabLayoutMediator(binding.tabview,binding.vp){ tab,position->
                      val itemTablayoutBinding=ItemTablayoutBinding.inflate(inflater)
                         itemTablayoutBinding.response=list[position]
                if(position!=0){
                    itemTablayoutBinding.root.background=null
                    itemTablayoutBinding.tv.setTextColor(Color.parseColor("#D0D0D0"))
                }
                tab.customView=itemTablayoutBinding.root
            }.attach()
            binding.springDotsIndicator.setViewPager2(binding.vp)

        })
        binding.vp.adapter=miniVpAdater
        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                historyRvAdapter.list = loadhistorylist(list[position].code)
                historyRvAdapter.notifyDataSetChanged()
            }
        })


        binding.tabview.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val itemTablayoutBinding=DataBindingUtil.getBinding<ItemTablayoutBinding>(tab?.customView!!)
                itemTablayoutBinding?.root?.background = resources.getDrawable(R.drawable.itemtabback)
                itemTablayoutBinding?.tv?.setTextColor(Color.WHITE)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val itemTablayoutBinding=DataBindingUtil.getBinding<ItemTablayoutBinding>(tab?.customView!!)
                itemTablayoutBinding?.root?.background = null
                itemTablayoutBinding?.tv?.setTextColor(Color.parseColor("#D0D0D0"))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        return binding.root
    }

    private fun setdefaultShared() {
          if(mySharedPreference.getsharedpreference()==""){
              mySharedPreference.putsharedprefence("USD")
          }
    }

    private fun addhistoryinformation(it: List<CurrencyModel>?) {
        if (appDatabase.historymodeldao().getallhistorymodels().isNotEmpty()) {
            if (appDatabase.historymodeldao().getallhistorymodels()
                    .reversed()[0].list[0].date != it!![0].date
            ) {
                val oneHistoryModel = HistoryModel(list = it)
                appDatabase.historymodeldao().addhistorymodel(oneHistoryModel)
            }
        } else {
            val oneHistoryModel = HistoryModel(list = it!!)
            appDatabase.historymodeldao().addhistorymodel(oneHistoryModel)
        }
    }


    override fun onResume() {
        mySharedPreference.putsharedprefence("USD")
        super.onResume()
    }
    fun startworkmanager(){
          val constraints=Constraints.Builder()
              .setRequiredNetworkType(NetworkType.UNMETERED)
              .setRequiresCharging(true)
              .build()
        val workRequest=PeriodicWorkRequest.Builder(MyWorker::class.java,15L,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        workManager.enqueue(workRequest)

    }

    fun loadhistorylist(code:String):List<CurrencyModel>{
        val list=ArrayList<CurrencyModel>()
        appDatabase.historymodeldao().getallhistorymodels().forEach {
            val index=it.list.indexOf(CurrencyModel("",code,"","","",""))
            list.add(it.list[index])
        }
        return list.reversed()
    }


}