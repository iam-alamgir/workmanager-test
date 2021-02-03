package com.cryptenet.wormanager_test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.cryptenet.wormanager_test.works.AutoKeepWork
import com.cryptenet.wormanager_test.works.AutoReplaceWork
import com.cryptenet.wormanager_test.works.ManualWork
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        view.findViewById<Button>(R.id.buttonManualReplaceWork).setOnClickListener {
            WorkManager.getInstance(requireContext()).cancelAllWorkByTag(MANUAL_WORK_TAG)

            WorkManager.getInstance(requireContext()).enqueue(
                PeriodicWorkRequestBuilder<ManualWork>(1, TimeUnit.MINUTES)
                    .addTag(MANUAL_WORK_TAG)
                    .setConstraints(constraint)
                    .build()
            )
        }

        view.findViewById<Button>(R.id.buttonAutoReplaceWork).setOnClickListener {
            WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
                AUTO_REPLACE_WORK_TAG,
                ExistingPeriodicWorkPolicy.REPLACE,
                PeriodicWorkRequestBuilder<AutoReplaceWork>(1, TimeUnit.MINUTES)
                    .addTag(AUTO_REPLACE_WORK_TAG)
                    .setConstraints(constraint)
                    .build()
            )
        }

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
                AUTO_KEEP_WORK_TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                PeriodicWorkRequestBuilder<AutoKeepWork>(1, TimeUnit.MINUTES)
                    .addTag(AUTO_KEEP_WORK_TAG)
                    .setConstraints(constraint)
                    .build()
            )
        }

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    companion object {
        const val MANUAL_WORK_TAG = "Manual Job"
        const val AUTO_REPLACE_WORK_TAG = "Auto replace Job"
        const val AUTO_KEEP_WORK_TAG = "Auto keep Job"
    }
}