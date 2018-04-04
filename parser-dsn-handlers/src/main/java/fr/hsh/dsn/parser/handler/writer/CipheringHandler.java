package fr.hsh.dsn.parser.handler.writer;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import fr.hsh.dsn.exception.ParseException;
import fr.hsh.dsn.parser.grammar.metamodel.Section;
import fr.hsh.dsn.parser.handler.IContentHandler;
import fr.hsh.utils.Cipherer;

public class CipheringHandler extends ComponentHandlerChainWriter {

	private final Cipherer		ciph;
	private final List<String>	fieldsToCipher	= new ArrayList<>();
	
	public CipheringHandler(final IContentHandler pNext, final Cipherer pCipherer) {
		super(pNext);
		this.ciph = pCipherer;
	}

	public CipheringHandler(final Cipherer pCipherer) {
		this.ciph = pCipherer;
	}

	@Override
	public void compute(final Section pSection, final String pValue) throws ParseException {
		String lCipheredValue = pValue;
		if (this.fieldsToCipher.contains(pSection.getName())) {
			try {
				lCipheredValue = this.ciph.aesEncode(lCipheredValue);
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
			}
		}
		super.compute(pSection, lCipheredValue);
	}

	@Override
	public void handleUnreferencedSection(final String pSectionName, final String pPayload) {
		String lCipheredValue = pPayload;
		if (this.fieldsToCipher.contains(pSectionName)) {
			try {
				lCipheredValue = this.ciph.aesEncode(lCipheredValue);
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
			}
		}
		super.handleUnreferencedSection(pSectionName, lCipheredValue);
	}

	public CipheringHandler addCipheringForField(final String pField) {
		this.fieldsToCipher.add(pField);
		return this;
	}
}
