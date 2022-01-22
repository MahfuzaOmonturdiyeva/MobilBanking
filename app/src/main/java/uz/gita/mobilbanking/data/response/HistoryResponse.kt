package uz.gita.mobilbanking.data.response


data class HistoryResponse(
    val pageNumber: Int,
    val pageSize: Int,
    val totalCount: Int,
    val data: List<HistoryItem>

)

data class HistoryItem(
    val sender:Int?,
    val receiver: Int?,
    val amount: Double,
    val time: Long,
    val fee: Double,
    val status: Int,
    var owner:String?=null
)

/*
"data": {
    "pageNumber": 0,
    "pageSize": 10,
    "totalCount": 7,
    "data": [
    {
        "receiver": 2,
        "amount": 500.0,
        "time": 1641330342681,
        "fee": 5.0,
        "status": 1
    },
    {
        "receiver": 1,
        "amount": 1.0,
        "time": 1641330604220,
        "fee": 0.01,
        "status": 1
    },
    {
        "receiver": 1,
        "amount": 1.0,
        "time": 1641330620518,
        "fee": 0.01,
        "status": 1
    },
    {
        "receiver": 1,
        "amount": 1.0,
        "time": 1641330666467,
        "fee": 0.01,
        "status": 1
    },
    {
        "receiver": 1,
        "amount": 1.0,
        "time": 1641330720864,
        "fee": 0.01,
        "status": 1
    },
    {
        "receiver": 3,
        "amount": 123.0,
        "time": 1641926435229,
        "fee": 1.23,
        "status": 1
    },
    {
        "receiver": 4,
        "amount": 50.0,
        "time": 1641930844611,
        "fee": 0.5,
        "status": 1
    }
    ]
}*/
