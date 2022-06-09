package lotto.domain

data class LottoTickets(
    val lottoCount: Int,
    val lottoTickets: List<LottoTicket>
) {
    init {
        require(lottoCount == lottoTickets.size) { "로또 수와 실제 로또 티켓 수가 달라요" }
    }

    fun match(winningNumbers: WinningNumber): Map<WinningInfo, Int> {
        return lottoTickets
            .map { lottoTicket -> lottoTicket.matchCount(winningNumbers) }
            .filter { WinningInfo.winningCounts().contains(it) }
            .groupingBy { count -> WinningInfo.findBy(count) }
            .eachCount()
    }
}
