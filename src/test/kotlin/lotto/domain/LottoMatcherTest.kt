package lotto.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LottoMatcherTest {
    @Test
    fun `LottoMatcher는 LottoTicket에 대한 당첨 정보를(당첨등수, 당첨갯수, 당첨금액) 제공한다`() {
        // given
        val lottoTickets = lottoTickets(
            lotto(1, 2, 3, 4, 5, 26),
            lotto(1, 2, 3, 4, 5, 36),
            lotto(1, 2, 3, 4, 35, 36),
            lotto(1, 2, 3, 34, 35, 36),
            lotto(1, 2, 33, 34, 35, 36),
            lotto(1, 32, 33, 34, 35, 36),
            lotto(31, 32, 33, 34, 35, 36),
        )
        val winningLotto = winningLotto(1, 2, 3, 4, 5, 6)
        val bonusNumber = BonusNumber(26)
        // when
        val lottoMatchResult = LottoMatcher().matchResult(lottoTickets, winningLotto, bonusNumber)
        // then
        val matchResult: Map<Rank, Count> = lottoMatchResult.matchResult
        val earnedMoney: EarnedMoney = lottoMatchResult.earnedMoney
        assertEquals(0, matchResult[Rank.FIRST]!!.count)
        assertEquals(1, matchResult[Rank.SECOND]!!.count)
        assertEquals(1, matchResult[Rank.THIRD]!!.count)
        assertEquals(1, matchResult[Rank.FOURTH]!!.count)
        assertEquals(1, matchResult[Rank.FIFTH]!!.count)
        assertEquals(3, matchResult[Rank.MISS]!!.count)
        assertEquals(31_555_000L, earnedMoney.money)
    }

    @Test
    fun `로또 당첨 결과기는 수익률 계산을 할 수 있다`() {
        // given
        val paidMoney = 14_000L
        val earnedMoney = 5_000L
        // when
        val earnedRate = LottoMatcher().calculateEarnedRate(EarnedMoney(earnedMoney), paidMoney)
        // then
        assertEquals(0.35714287f, earnedRate.rate)
    }

    companion object {
        fun lotto(vararg numbers: Int): LottoTicket = LottoTicket(numbers.toList())
        fun lottoTickets(vararg lotto: LottoTicket) = LottoTickets(lotto.toList())
        fun winningLotto(vararg numbers: Int): WinningNumber = WinningNumber(numbers.toList())
    }
}
