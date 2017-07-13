package web.action.product;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import service.product.ProductSearchService;

import bean.PageBean;
import bean.QueryResult;
import bean.product.ProductInfo;
import web.action.BaseAction;
/**
 * ÉÌÆ·ËÑË÷
 */
@Controller
public class ProductSearchAction extends BaseAction<PageBean> {
	@Resource
	private ProductSearchService productSearchService;
	private String word;
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}


	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		QueryResult<ProductInfo> qr=productSearchService.query(word, model.getCurrentpage(), model.getPagesize());
		model.setList(qr.getList());
		model.setTotalrecord(qr.getTotalrecord());
		
		request.put("pagebean", model);
		return SUCCESS;
	}
	
	
	

}
