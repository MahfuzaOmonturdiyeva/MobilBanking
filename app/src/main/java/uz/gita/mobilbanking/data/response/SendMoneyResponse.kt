package uz.gita.mobilbanking.data.response

data class SendMoneyResponse(
	val amount: Double,
	val receiver: Int,
	val sender: Int,
	val fee: Double,
	val id: Int,
	val time: Long,
	val status: Int
)

