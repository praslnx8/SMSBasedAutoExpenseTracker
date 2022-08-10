package app.expense.domain.di

import app.expense.api.CategoryAPI
import app.expense.api.ExpenseAPI
import app.expense.api.PaidToAPI
import app.expense.api.SMSReadAPI
import app.expense.api.SuggestionSyncAPI
import app.expense.api.SuggestionsAPI
import app.expense.domain.categories.FetchCategoriesUseCase
import app.expense.domain.expense.AddExpenseUseCase
import app.expense.domain.expense.DeleteExpenseUseCase
import app.expense.domain.expense.FetchExpenseUseCase
import app.expense.domain.mappers.DataMapper
import app.expense.domain.paidTo.FetchPaidToUseCase
import app.expense.domain.suggestion.DeleteSuggestionUseCase
import app.expense.domain.suggestion.FetchSuggestionUseCase
import app.expense.domain.suggestion.SyncSuggestionUseCase
import app.expense.domain.suggestion.detector.SuggestionDetector
import app.expense.domain.suggestion.detector.SuggestionDetectorImpl
import app.expense.domain.suggestion.detector.SuggestionParserHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DomainModule {

    @Provides
    fun provideSuggestionParserHelper(): SuggestionParserHelper {
        return SuggestionParserHelper()
    }

    @Provides
    fun provideDataMapper(): DataMapper {
        return DataMapper()
    }

    @Provides
    fun provideSuggestionDetector(suggestionParserHelper: SuggestionParserHelper): SuggestionDetector {
        return SuggestionDetectorImpl(suggestionParserHelper)
    }

    @Provides
    fun suggestionSyncService(
        suggestionSyncAPI: SuggestionSyncAPI,
        smsReadAPI: SMSReadAPI,
        suggestionDetector: SuggestionDetector
    ): SyncSuggestionUseCase {
        return SyncSuggestionUseCase(suggestionSyncAPI, smsReadAPI, suggestionDetector)
    }

    @Provides
    fun fetchSuggestionUseCase(
        suggestionsAPI: SuggestionsAPI,
        dataMapper: DataMapper
    ): FetchSuggestionUseCase {
        return FetchSuggestionUseCase(suggestionsAPI, dataMapper)
    }

    @Provides
    fun fetchExpenseUseCase(expenseAPI: ExpenseAPI, dataMapper: DataMapper): FetchExpenseUseCase {
        return FetchExpenseUseCase(expenseAPI, dataMapper)
    }

    @Provides
    fun addExpenseUseCase(
        expenseAPI: ExpenseAPI,
        suggestionsAPI: SuggestionsAPI,
        categoryAPI: CategoryAPI,
        paidToAPI: PaidToAPI
    ): AddExpenseUseCase {
        return AddExpenseUseCase(expenseAPI, suggestionsAPI, categoryAPI, paidToAPI)
    }

    @Provides
    fun getCategoriesUseCase(categoryAPI: CategoryAPI) = FetchCategoriesUseCase(categoryAPI)

    @Provides
    fun getPaidToUseCase(paidToAPI: PaidToAPI) = FetchPaidToUseCase(paidToAPI)

    @Provides
    fun getDeleteExpenseUseCase(expenseAPI: ExpenseAPI) = DeleteExpenseUseCase(expenseAPI)

    @Provides
    fun getDeleteSuggestionUseCase(suggestionsAPI: SuggestionsAPI) =
        DeleteSuggestionUseCase(suggestionsAPI)
}
