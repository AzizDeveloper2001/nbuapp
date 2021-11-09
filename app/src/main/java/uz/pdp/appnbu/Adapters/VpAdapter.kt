package uz.pdp.appnbu.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.pdp.appnbu.fragments.AllCurrenciesFragment
import uz.pdp.appnbu.fragments.CalculatorFragment
import uz.pdp.appnbu.fragments.MainWindowFragment

class VpAdapter(fragment:Fragment,val opencalcpage:AllCurrenciesFragment.Opencalcpage):
    FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->MainWindowFragment()
            1->AllCurrenciesFragment(opencalcpage)
            else -> CalculatorFragment()
        }
    }

}