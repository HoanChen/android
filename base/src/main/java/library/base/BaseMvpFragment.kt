package library.base

import android.os.Bundle
import library.base.utils.MyProgressDialog
import library.base.utils.ProgressDialogUtil
import library.base.utils.ToastUtil

/**
 * MVP模式Fragment的基类
 */
abstract class BaseMvpFragment<P : IBasePresenter> : BaseFragment(), IBaseView {

    protected lateinit var presenter: P

    private var loadingDialog: MyProgressDialog? = null

    protected abstract fun createPresenter(): P

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        presenter = createPresenter()
        lifecycle.addObserver(presenter)
    }

    override fun showLoading() {
        val context = context
        if (context != null && null == loadingDialog) {
            loadingDialog = ProgressDialogUtil.getProgressDialog(context)
        }
        if (loadingDialog?.isShowing == false) {
            loadingDialog?.show()
        }
        loadingDialog?.setText(null)
    }

    override fun showLoading(msg: String) {
        val context = context
        if (context != null && null == loadingDialog) {
            loadingDialog = ProgressDialogUtil.getProgressDialog(this.context!!)
        }
        if (loadingDialog?.isShowing == false) {
            loadingDialog?.show()
        }
        loadingDialog?.setText(msg)
    }

    override fun dismissLoading() {
        ProgressDialogUtil.dismissDialog(loadingDialog)
    }

    override fun showToast(msg: String) {
        ToastUtil.showToast(msg)
    }

    override fun showToast(strId: Int) {
        ToastUtil.showToast(strId)
    }
}