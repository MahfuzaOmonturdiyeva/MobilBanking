package uz.gita.mobilbanking.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import uz.gita.mobilbanking.data.response.HistoryItem
import uz.gita.mobilbanking.databinding.ItemTransfersHistoryIncomesBinding
import uz.gita.mobilbanking.databinding.ItemTransfersHistoryOutcomesBinding
import java.text.SimpleDateFormat
import java.util.*

class TransfersHistoryAdapter :
    PagingDataAdapter<HistoryItem, TransfersHistoryAdapter.ViewHolder>(MyDiffUtil) {
    val INCOME_VIEW = 1
    val OUTCOME_VIEW = 2

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind()
    }

    inner class IncomeViewHolder(private val itemIncomeBinding: ItemTransfersHistoryIncomesBinding) :
        ViewHolder(itemIncomeBinding.root) {

        @SuppressLint("SetTextI18n")
        override fun bind() {
            val data = getItem(absoluteAdapterPosition)
            data?.let {
                //itemIncomeBinding.tvPanSender.visibility=View.GONE
                itemIncomeBinding.tvPanSender.text = it.owner
                itemIncomeBinding.tvBalanceSender.text = "+" + it.amount.toString()
                val sdf = SimpleDateFormat("HH:mm:ss, MM/dd/yyyy")
                val netDate = Date(it.time)
                val time = sdf.format(netDate)
                itemIncomeBinding.time.text = time
            }
        }
    }

    inner class OutcomeViewHolder(private val itemOutcomeBinding: ItemTransfersHistoryOutcomesBinding) :
        ViewHolder(itemOutcomeBinding.root) {
        @SuppressLint("SetTextI18n")
        override fun bind() {
            val data = getItem(absoluteAdapterPosition)
            data?.let {
                // itemOutcomeBinding.tvPanReceiver.visibility=View.GONE
                itemOutcomeBinding.tvPanReceiver.text = it.owner
                val balance = it.amount + it.fee
                itemOutcomeBinding.tvBalanceReceiver.text = "+$balance"
                val sdf = SimpleDateFormat("HH:mm:ss, MM/dd/yyyy")
                val netDate = Date(it.time)
                val time = sdf.format(netDate)
                itemOutcomeBinding.time.text = "Time: ${it.time}"
            }
        }
    }

    object MyDiffUtil : DiffUtil.ItemCallback<HistoryItem>() {
        override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemViewType(position: Int): Int {
        Timber.d("getItemViewType: $position")
        val sender = getItem(position)?.sender
        return if (sender == null) {
            OUTCOME_VIEW
        } else INCOME_VIEW
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            INCOME_VIEW -> IncomeViewHolder(
                ItemTransfersHistoryIncomesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> OutcomeViewHolder(
                ItemTransfersHistoryOutcomesBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }

}