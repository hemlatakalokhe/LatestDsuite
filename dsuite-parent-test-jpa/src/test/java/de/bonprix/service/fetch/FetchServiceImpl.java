package de.bonprix.service.fetch;

import java.util.List;

import javax.annotation.Resource;

import de.bonprix.service.AbstractFetchService;

public class FetchServiceImpl extends
		AbstractFetchService<FetchEntity, Fetch, FetchFilter, FetchOptions, FetchRepository> implements FetchService {

	public FetchServiceImpl() {
		super(FetchEntity.class, Fetch.class, FetchFilter.class, FetchOptions.class);
	}

	@Resource
	private FetchRepository exampleLanguageRepository;

	@Override
	protected FetchRepository getRepository() {
		return this.exampleLanguageRepository;
	}

	@Override
	public List<Fetch> findAll(FetchFilter filter, FetchOptions fetchOptions) {
		return super.findAllDefault(filter, fetchOptions);
	}

	@Override
	public Fetch findById(Long id, FetchOptions fetchOptions) {
		return super.findByIdDefault(id, fetchOptions);
	}

	@Override
	public void deleteById(Long id) {
		super.deleteByIdDefault(id);
	}

	@Override
	public long create(Fetch fetch) {
		return super.createDefault(fetch);
	}

	@Override
	public void update(Long id, Fetch fetch) {
		super.updateDefault(id, fetch);
	}

}
