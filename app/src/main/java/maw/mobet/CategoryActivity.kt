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
import splitties.resources.strArray

class CategoryActivity : AppCompatActivity(), MyAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val drawableArr = resources.obtainTypedArray(R.array.category_drawable)
        val titleArr = strArray(R.array.category)

        val categoryList = mutableListOf<CategoryListItem>()
        for (i in titleArr.indices) {
            if (titleArr[i] == null) {
                continue
            }
            val item = CategoryListItem(titleArr[i], drawableArr.getResourceId(i, 0), i)
            categoryList.add(item)
        }

        list_view.layoutManager = GridLayoutManager(this, 3)
        list_view.addItemDecoration(MyItemDecoration())
        list_view.adapter = MyAdapter(categoryList, this)

        drawableArr.recycle()
    }

    override fun onItemClick(view: View) {
        val intent = Intent()
        intent.putExtra("result", (view.tag as CategoryListItem).position)
        setResult(RESULT_OK, intent)
        finish()
    }
}
