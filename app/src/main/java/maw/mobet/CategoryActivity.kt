package maw.mobet

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_category.*
import maw.mobet.category.CategoryListItem
import maw.mobet.category.MyAdapter
import maw.mobet.category.MyItemDecoration

class CategoryActivity : AppCompatActivity(), MyAdapter.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val drawableArr = resources.obtainTypedArray(R.array.category_drawable)
        val titleArr = resources.getStringArray(R.array.category)

        val categoryList = mutableListOf<CategoryListItem>()
        for (i in titleArr.indices) {
            val item = CategoryListItem(titleArr[i], drawableArr.getResourceId(i, -1))
            categoryList.add(item)
        }

        list_view.layoutManager = GridLayoutManager(this, 3)
        list_view.addItemDecoration(MyItemDecoration(this))
        list_view.adapter = MyAdapter(categoryList, this)

        drawableArr.recycle()
    }

    override fun onClick(view: View) {
        val intent = Intent()
        if (view == all_btn) {
            intent.putExtra("result", -1)
        } else {
            intent.putExtra("result", view.tag as Int)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}
