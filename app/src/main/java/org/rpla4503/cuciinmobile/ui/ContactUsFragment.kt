package org.rpla4503.cuciinmobile.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.rpla4503.cuciinmobile.databinding.FragmentContactusBinding
import org.rpla4503.cuciinmobile.databinding.FragmentDashboardBinding
import org.rpla4503.cuciinmobile.db.DatabaseHandler

class ContactUsFragment: Fragment() {
    private lateinit var binding: FragmentContactusBinding
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactusBinding.inflate(inflater, container, false)
        databaseHandler = DatabaseHandler(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}