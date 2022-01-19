package uz.gita.mobilbanking.ui.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.mobilbanking.R
import uz.gita.mobilbanking.data.response.CardInfoResponse
import uz.gita.mobilbanking.databinding.ItemOneCardBinding
import uz.gita.mobilbanking.utils.showToast


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

    inner class CardViewHolder(private val binding: ItemOneCardBinding,
                               private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            getItem(absoluteAdapterPosition)?.apply {
                setColorCard(this.color)
                binding.imgHumoOneCardItem.visibility = View.GONE
                binding.tvNameOneCardItem.text = this.cardName
                binding.tvValidityDateOneCardItem.text = this.exp
                var textPan = ""
                this.pan.forEachIndexed { index, c ->
                    if (index <= 5 || index >= 12) {
                        if(index%4==0)
                            textPan+=" "
                        textPan += c.toString()
                    }
                    else {
                        if(index%4==0)
                            textPan+=" "
                        textPan+="*"}
                }

                binding.tvPanOneCardItem.text = textPan
                if (!this.ignoreBalance) {
                    binding.tvBalanceOneCardItem.text = this.balance.toString()
                    binding.tvTextUzsOneCardItem.visibility = View.VISIBLE
                } else {
                    binding.tvBalanceOneCardItem.text = "Balance not enabled"
                    binding.tvTextUzsOneCardItem.visibility = View.INVISIBLE
                }
            }
        }
        private fun copyClipBoard() {
            val clipboard: ClipboardManager? =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText(
                binding.tvPanOneCardItem.text.toString(),
                binding.tvPanOneCardItem.text.toString()
            )
            clip?.let {
                clipboard?.setPrimaryClip(clip)
                getItem(absoluteAdapterPosition)?.apply{
                    val clipboard: ClipboardManager? =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                    val clip = ClipData.newPlainText(this.pan, this.pan)
                    clip?.let {
                        clipboard?.setPrimaryClip(clip)
                        Toast.makeText(context, "Card number copied", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        private fun setColorCard(colorCard: Int) {
            when (colorCard) {
                0 -> binding.cvOneCard1.setBackgroundResource(R.color.white)
                1 -> binding.cvOneCard1.setBackgroundResource(R.color.card_color1)
                2 -> binding.cvOneCard1.setBackgroundResource(R.color.card_color2)
                3 -> binding.cvOneCard1.setBackgroundResource(R.color.card_color3)
                4 -> binding.cvOneCard1.setBackgroundResource(R.color.card_color4)
                5 -> binding.cvOneCard1.setBackgroundResource(R.color.card_color5)
                6 -> binding.cvOneCard1.setBackgroundResource(R.color.card_color6)
                7 -> binding.cvOneCard1.setBackgroundResource(R.color.card_color7)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        CardViewHolder(
            ItemOneCardBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent.context
        )

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind()
        holder.itemView.findViewById<CardView>(R.id.cv_one_card_item)
            .setOnClickListener {
                onclickOnCardListener(getItem(position))
            }
    }
}