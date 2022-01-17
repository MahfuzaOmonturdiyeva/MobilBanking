package uz.gita.mobilbanking.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.databinding.ItemOneCardBinding


class CardAdapter(private val onclickOnCardListener: ((CardInfoResponse) -> Unit)) :
    ListAdapter<CardInfoResponse, CardAdapter.CardViewHolder>(DiffItem) {

    object DiffItem : DiffUtil.ItemCallback<CardInfoResponse>() {
        override fun areItemsTheSame(
            oldItem: CardInfoResponse,
            newItem: CardInfoResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CardInfoResponse,
            newItem: CardInfoResponse
        ): Boolean {
            return oldItem == newItem
        }
    }


    inner class CardViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        private val binding by viewBinding(ItemOneCardBinding::bind)

        fun bind() {
            getItem(absoluteAdapterPosition)?.apply {
                binding.imgHumo.visibility = View.GONE
                binding.tvNameCardItem.text = this.cardName
                binding.tvValidityDateItem.text = this.exp
                var textPan = ""
                this.pan.forEachIndexed { index, c ->
                    if (index <= 5 || index >= 12)
                        textPan += c.toString()
                }

                binding.tvPanCardItem.text = textPan
                if (!this.ignoreBalance) {
                    binding.tvBalanceCardItem.text = this.balance.toString()
                    binding.tvTextUzs.visibility = View.VISIBLE
                } else {
                    binding.tvBalanceCardItem.text = "Balance not enabled"
                    binding.tvTextUzs.visibility = View.INVISIBLE
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        CardViewHolder(
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.screen_card_one_card, parent, false
            )
        )

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind()
        holder.itemView.findViewById<ConstraintLayout>(R.id.cv_one_card_item)
            .setOnClickListener {
                onclickOnCardListener(getItem(position))
            }
    }
}