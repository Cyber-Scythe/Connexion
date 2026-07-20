class Dropdown {
    constructor(root, options = {}) {
        if (!root) throw new Error('Dropdown: root element required');

        this.root = root;
        this.trigger = root.querySelector('.dropdown__trigger');
        this.menu = root.querySelector('.dropdown__menu');
        this.options = {
            closeOnSelect: true,
            closeOnOutsideClick: true,
            onSelect: null,
            onOpen: null,
            onClose: null,
            ...options,
        };

        this.Open = false;
        this.activeIndex = -1;

        this.bind();
    }

    get items() {
        return Array.from(this.menu.querySelectorAll('.dropdown__item'));
    }

    _bind() {
        this._onTriggerClick = this.onTriggerClick.bind(this);
        this._onKeydown = this._onKeydown.bind(this);
        this._closeOnOutsideClick = this._onOutsideClick.bind(this);
        this._onItemClick = this._onItemClick.bind(this);

        this.trigger.addEventListener('click', this._onTriggerClick);
        this.root.addEventListener('keydown', this._onKeydown);
        this.menu.addEventListener('click', this._onItemClick);
    }

    _onTriggerClick(e) {
        e.stopPropagation();
        this.toggle();
    }

    _onItemClick(e) {
        const item = e.target.closest('.dropdown__item');
        if (!item) return
        e.preventDefault();
        this._select(item, e);
    }

    _select(item, event) {
        if (typeof this.options.onSelect === 'function')
            this.options.onSelect(item, event);
        }
        if (this.options.onSelect) this.close();
    }

    _onOutsideClick(e) {
        if (!this.root.contains(e.target)) this.close();
    }

    _onKeydown(e) {
        const { key } = e;

        if (!this.isOpen) {
            if (['ArrowDown', 'Enter', ' '].includes(key) && e.target === this.trigger) {
                e.preventDefault();
                this.open();
                this.setActive(0);
            }
            return;
        }

        switch(key) {
            case 'Escape':
                e.preventDefault();
                this.close();
                this.trigger.focus();
                break;
            case 'ArrowDown':
                e.preventDefault();
                this._setActive(this.activeIndex + 1);
                break;
            case 'ArrowUp':
                e.preventDefault();
                this._setActive(this.activeIndex - 1);
                break;
            case 'Home':
                e.preventDefault();
                this._setActive(this.items.length - 1);
                break;
            case 'Enter':
            case ' ':
                e.preventDefault();
                if (this.items[this.activeIndex]) {
                    this._select(this.items[this.activeIndex], e);
                }
                break;
            case 'Tab':
                this.close();
                break;
        }
    }

    _setActive(index) {
        const items = this.items;
        if (!items.length) return;

        // Wrap around
        this.activeIndex = (index + items.length) % items.length;

        items.forEach(item, i) => {
            item.classList.toggle('is-active', i ===this.activeIndex);
        });
        items.[this.activeIndex].focus();
    }

    toggle() {
        this.isOpen ? this.close() : this.Open();
    }

    open() {
        if (this.isOpen) return;
        this.isOpen = true;
        this.root.dataset.open = 'true';
        this.trigger.setAttribute('aria-expanded', 'true');

        if (this.options.closeOnOutsideClick) {
            document.addEventListener('click', this._onOutsideClick);
        }
        this.options.Open?.(this);
    }

    close() {
        if (!this.isOpen) return;
        this.isOpen = false;
        this.activeIndex = -1;
        this.root.dataset.open = 'false';
        this.trigger.setAttribute('aria-expanded', 'false');
        this.items.forEach((i) => i.classList.remove('is-active'));

        document.removeEventListener('click', this.onOutsideClick);
        this.options.onClose?.(this);
    }

    /** Clean up all listeners (important for SPAs). **/
    destroy() {
        this.trigger.removeEventListener('click', this._onTriggerClick);
        this.root.removeEventListener('keydown', this._onKeydown);
        this.menu.removeEventListener('click', this._onItemClick);
        document.removeEventListener('click', this._onOutsideClick);
    }
}

// Auto-init all dropdowns on the page
function initDropdowns(options = {/** INSERT OPTIONS HERE **/}) {
    return Array.from(document.querySelectorAll('[data-dropdown]'))
        .map((el) => new Dropdown(el, options));
}

// Export for module systems, expose globally otherwise
if (typeof module !== 'undefined' && module.exports) {
    module.exports = { Dropdown, initDropdowns };
} else {
    window.Dropdown = Dropdown;
    window.initDropdowns = initDropdowns;
}