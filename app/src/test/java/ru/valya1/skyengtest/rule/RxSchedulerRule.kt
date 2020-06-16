package ru.valya1.skyengtest.rule

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Callable

class RxSchedulersRule : TestRule {
    private val scheduler: Scheduler
        get() = Schedulers.trampoline()
    
    private val mRxAndroidSchedulersHook = io.reactivex.functions.Function<Callable<Scheduler>, Scheduler> { scheduler }
    private val mRxJavaImmediateScheduler = io.reactivex.functions.Function<Scheduler, Scheduler> { scheduler }
    
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxAndroidPlugins.reset()
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(mRxAndroidSchedulersHook)
                
                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler(mRxJavaImmediateScheduler)
                RxJavaPlugins.setComputationSchedulerHandler(mRxJavaImmediateScheduler)
                RxJavaPlugins.setNewThreadSchedulerHandler(mRxJavaImmediateScheduler)
                
                base.evaluate()
                
                RxAndroidPlugins.reset()
                RxJavaPlugins.reset()
            }
        }
    }
}