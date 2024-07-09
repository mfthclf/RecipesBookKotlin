package com.mfthc.yemekkitabikotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.mfthc.yemekkitabikotlin.adapter.TarifAdapter
import com.mfthc.yemekkitabikotlin.databinding.FragmentListeBinding
import com.mfthc.yemekkitabikotlin.model.Tarif
import com.mfthc.yemekkitabikotlin.roomdb.TarifDAO
import com.mfthc.yemekkitabikotlin.roomdb.TarifDataBase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class ListeFragment : Fragment() {

    var _binding: FragmentListeBinding? = null
    val binding get() = _binding!!
    private lateinit var db: TarifDataBase
    private lateinit var tarifDao: TarifDAO
    private val mDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(requireContext(), TarifDataBase::class.java, "Tarifler").build()
        tarifDao = db.tarifDao()

    }

    private fun handleResponse(tarifler: List<Tarif>) {
        val adapter = TarifAdapter(tarifler)
        binding.tarifRecyclerView.adapter = adapter
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListeBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener { yeniEkle(it) }
        binding.tarifRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        verileriAl()


    }

    private fun verileriAl() {

        mDisposable.add(
            tarifDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )

    }


    fun yeniEkle(view: View) {
        val action =
            ListeFragmentDirections.actionListeFragment2ToTarifFragment(bilgi = "yeni", id = -1)
        Navigation.findNavController(view).navigate(action)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mDisposable.clear()

    }
}