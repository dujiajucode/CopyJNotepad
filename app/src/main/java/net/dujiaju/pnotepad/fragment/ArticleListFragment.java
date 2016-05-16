package net.dujiaju.pnotepad.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.dujiaju.pnotepad.R;
import net.dujiaju.pnotepad.model.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lilujia on 16/5/15.
 */
public class ArticleListFragment extends Fragment {

    public static final String ARTICLE_LIST_FRAGMENT_FOLDER = "ARTICLE_LIST_FRAGMENT_FOLDER";

    private List<Article> mArticlesList;

    private String mTitle;

    private View mView;

    private MyAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState = getArguments();
        if (savedInstanceState != null) {
            mTitle = savedInstanceState.getString(ARTICLE_LIST_FRAGMENT_FOLDER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        mView = view;
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mArticlesList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Article article = new Article();
            article.setTitle(UUID.randomUUID().
                    toString().replaceAll("-", ""));
            article.setID(UUID.randomUUID());
            mArticlesList.add(article);
        }
        mAdapter = new MyAdapter();

        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(),
                LinearLayoutManager.HORIZONTAL));

        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        return view;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_article_list_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            holder.mTitleView.setText(mArticlesList.get(position).getTitle());
            holder.mTitleView.setTag(mArticlesList.get(position));
        }

        @Override
        public int getItemCount() {
            return mArticlesList == null ? 0 : mArticlesList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public TextView mTitleView;

            public ViewHolder(View v) {
                super(v);
                mTitleView = (TextView) v.findViewById(R.id.tvTitle);
                Drawable drawable1 = getResources().getDrawable(R.drawable.file);
                drawable1.setBounds(0, 0, 100, 100);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
                mTitleView.setCompoundDrawables(drawable1, null, null, null);//只放左边
                mTitleView.setOnLongClickListener(new ItemLongClickListener());
                mTitleView.setOnClickListener(new ItemClickListener());
            }
        }

        public void addItem(Article article, int position) {
            mArticlesList.add(position, article);
            notifyItemInserted(position); //Attention!
        }

        public void removeItem(final Article article) {
            final int position = mArticlesList.indexOf(article);
            mArticlesList.remove(position);
            notifyItemRemoved(position);
            Snackbar.make(mView, R.string.notepad_delete, Snackbar.LENGTH_LONG)
                    .setAction(R.string.notepad_undo, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //撤销
                            addItem(article, position);
                        }
                    }).show();
        }
    }

    class ItemLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {

            if (v.getTag() != null) {
                Article article = (Article) v.getTag();
                UUID id = mArticlesList.get(mArticlesList.indexOf(article)).getID();
                mAdapter.removeItem(article);
            }
            return true;
        }
    }

    class ItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getTag() != null) {
                Snackbar.make(mView, "open", Snackbar.LENGTH_LONG).show();
            }
        }
    }


    class RecycleViewDivider extends RecyclerView.ItemDecoration {

        private Paint mPaint;
        private Drawable mDivider;
        private int mDividerHeight = 2;//分割线高度，默认为1px
        private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
        private final int[] ATTRS = new int[]{android.R.attr.listDivider};

        /**
         * 默认分割线：高度为2px，颜色为灰色
         *
         * @param context
         * @param orientation 列表方向
         */
        public RecycleViewDivider(Context context, int orientation) {
            if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
                throw new IllegalArgumentException("请输入正确的参数！");
            }
            mOrientation = orientation;

            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        /**
         * 自定义分割线
         *
         * @param context
         * @param orientation 列表方向
         * @param drawableId  分割线图片
         */
        public RecycleViewDivider(Context context, int orientation, int drawableId) {
            this(context, orientation);
            mDivider = ContextCompat.getDrawable(context, drawableId);
            mDividerHeight = mDivider.getIntrinsicHeight();
        }

        /**
         * 自定义分割线
         *
         * @param context
         * @param orientation   列表方向
         * @param dividerHeight 分割线高度
         * @param dividerColor  分割线颜色
         */
        public RecycleViewDivider(Context context, int orientation, int dividerHeight, int dividerColor) {
            this(context, orientation);
            mDividerHeight = dividerHeight;
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(dividerColor);
            mPaint.setStyle(Paint.Style.FILL);
        }


        //获取分割线尺寸
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, mDividerHeight);
        }

        //绘制分割线
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        //绘制横向 item 分割线
        private void drawHorizontal(Canvas canvas, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + layoutParams.bottomMargin;
                final int bottom = top + mDividerHeight;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }

        //绘制纵向 item 分割线
        private void drawVertical(Canvas canvas, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + layoutParams.rightMargin;
                final int right = left + mDividerHeight;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }
    }
}
