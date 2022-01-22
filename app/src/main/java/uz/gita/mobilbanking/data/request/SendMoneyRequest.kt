package uz.gita.mobilbanking.data.request

data class SendMoneyRequest(
	val amount: Int,
	val receiverPan: String,
	var sender: Int
)

