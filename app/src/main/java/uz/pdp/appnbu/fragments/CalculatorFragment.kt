package uz.pdp.appnbu.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import uz.pdp.appnbu.Adapters.SpinnAdapter
import uz.pdp.appnbu.R
import uz.pdp.appnbu.Utils.MySharedPreference
import uz.pdp.appnbu.ViewModel.MyViewModel
import uz.pdp.appnbu.databinding.FragmentCalculatorBinding
import uz.pdp.appnbu.databinding.SpinnerItemBinding
import uz.pdp.appnbu.models.CurrencyModel
import java.text.DecimalFormat


class CalculatorFragment : Fragment(),AdapterView.OnItemSelectedListener {

    lateinit var binding: FragmentCalculatorBinding
    lateinit var myViewModel: MyViewModel
    lateinit var mySharedPreference: MySharedPreference
    lateinit var spinnAdapter: SpinnAdapter

    lateinit var list: ArrayList<CurrencyModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCalculatorBinding.inflate(inflater,container,false)
        myViewModel=ViewModelProvider(this)[MyViewModel::class.java]
        mySharedPreference=MySharedPreference.getinstance(requireContext())
        list= ArrayList(listOf(CurrencyModel("1","UZS","","1","1","O'zbekistan so'mi")))
        spinnAdapter= SpinnAdapter(list)
        binding.apply {
            spin1.adapter=spinnAdapter
            spin2.adapter=spinnAdapter
            myViewModel.getretrofitlist(requireContext()).observe(viewLifecycleOwner, Observer{
                list.addAll(it)
                spinnAdapter.notifyDataSetChanged()
               val current=mySharedPreference.getsharedpreference()
                val index=list.indexOf(CurrencyModel("",current,"","","",""))

                spin1.setSelection(index)
            })

            convert.setOnClickListener {
                val one=spin1.selectedItemPosition
                val two=spin2.selectedItemPosition
                spin1.setSelection(two)
                spin2.setSelection(one)
            }
            edit1.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    calculation()
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
            spin1.onItemSelectedListener=this@CalculatorFragment
            spin2.onItemSelectedListener=this@CalculatorFragment
        }
        return binding.root
    }
    override fun onResume() {
        val currentCurrency = mySharedPreference.getsharedpreference()

        val index = list.indexOf(CurrencyModel("", currentCurrency, "", "", "", ""))

        binding.spin1.setSelection(index)
        super.onResume()
    }

 fun calculation() {
     var fm = DecimalFormat("#.##")
     val firstvalue = list[binding.spin1.selectedItemPosition]
     val secondvalue = list[binding.spin2.selectedItemPosition]

     binding.apply {
         if (edit1.text.isEmpty()) {
             cell.text = "0.00 ${secondvalue.code}"
             buy.text = "0.00 ${secondvalue.code}"
         } else {
             if(firstvalue.nbu_cell_price=="" && secondvalue.nbu_cell_price==""){
                 var diff=firstvalue.cb_price.toDouble()/secondvalue.cb_price.toDouble()
                 var value=diff * edit1.text.toString().toDouble()
                 cell.text="${fm.format(value)} ${secondvalue.code}"
                 buy.text="${fm.format(value)} ${secondvalue.code}"
             } else if(firstvalue.nbu_cell_price!="" && secondvalue.nbu_cell_price!=""){
                 var diffcell=firstvalue.nbu_cell_price.toDouble()/secondvalue.nbu_cell_price.toDouble()
                 var diffbuy=firstvalue.nbu_buy_price.toDouble()/secondvalue.nbu_buy_price.toDouble()
                 var valuecell= diffcell*edit1.text.toString().toDouble()
                 var valuebuy=diffbuy*edit1.text.toString().toDouble()
                 cell.text="${fm.format(valuecell)} ${secondvalue.code}"
                 buy.text="${fm.format(valuebuy)} ${secondvalue.code}"
             } else if(firstvalue.nbu_cell_price==""){
                 var cell=firstvalue.cb_price.toDouble()/secondvalue.nbu_cell_price.toDouble()
                 var buy= firstvalue.cb_price.toDouble()/secondvalue.nbu_buy_price.toDouble()
                 var vcell= cell*edit1.text.toString().toDouble()
                 var vbuy= buy* edit1.text.toString().toDouble()
                 binding.cell.text="${fm.format(vcell)} ${secondvalue.code}"
                 binding.buy.text="${fm.format(vbuy)} ${secondvalue.code}"
             } else if(secondvalue.nbu_cell_price==""){
                 var c=firstvalue.nbu_cell_price.toDouble()/secondvalue.cb_price.toDouble()
                 var b=firstvalue.nbu_buy_price.toDouble()/secondvalue.cb_price.toDouble()
                 var vc=c* edit1.text.toString().toDouble()
                 var vb=b* edit1.text.toString().toDouble()
                 cell.text="${fm.format(vc)} ${secondvalue.code}"
                 buy.text="${fm.format(vb)} ${secondvalue.code}"
             }
         }
     }

 }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        calculation()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}